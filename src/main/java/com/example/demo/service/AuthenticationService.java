package com.example.demo.service;

import com.example.demo.dao.InvalidatedTokenDAO;
import com.example.demo.dao.RoleDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.TokenRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.ValidateTokenResponse;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.InvalidatedToken;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
public class AuthenticationService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final InvalidatedTokenDAO invalidatedTokenDAO;

    private final PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public AuthenticationService(UserDAO userDAO, RoleDAO roleDAO, InvalidatedTokenDAO invalidatedTokenDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.invalidatedTokenDAO = invalidatedTokenDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public ValidateTokenResponse validateToken(TokenRequest request) {
        String token = request.getToken();
        boolean isValid = false;
        Date expiry = null;

        try {
            SignedJWT signedJWT = verifyToken(token, false);

            isValid = true;
            expiry = signedJWT.getJWTClaimsSet().getExpirationTime();
        } catch (Exception ignored) {
            log.info("Token is invalid");
        }

        return ValidateTokenResponse.builder().valid(isValid).expiry(expiry).build();
    }

    private void invalidateToken(SignedJWT signedJWT) throws ParseException {
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiry = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken token = InvalidatedToken.builder()
                .jit(jit)
                .expiry(expiry.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();

        if (invalidatedTokenDAO.isInvalidated(jit))
            return;

        invalidatedTokenDAO.invalidateToken(token);
    }

    public void logout(TokenRequest request) throws ParseException, JOSEException {
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), true);

            invalidateToken(signedJWT);
        } catch (AppException e) {
            log.info("Token is already expired.");
        }
    }

    public AuthenticationResponse refreshToken(TokenRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        invalidateToken(signedJWT);

        String username = signedJWT.getJWTClaimsSet().getSubject();

        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            throw new AppException(ErrorCode.UNCATEGORIZED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userDAO.getUserByEmail(request.getEmail());

        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String jwtToken = generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).authenticated(true).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("demo.example.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildUserScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (Exception e) {
            log.error("Failed to generate token", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY);

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = isRefresh
                ? Date.from(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS))
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);


        if (!(verified && expiryTime.toInstant().isAfter(Instant.now()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenDAO.isInvalidated(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private String buildUserScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        List<Role> roleList = roleDAO.getRolesByUserId(user.getUserId());

        roleList.stream().map(Role::getRoleName).forEach(stringJoiner::add);

        return stringJoiner.toString();
    }

}
