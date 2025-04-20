package com.example.demo.controller;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.TokenRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.ValidateTokenResponse;
import com.example.demo.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public static final String MSG_AUTH_SUCCESS = "Authentication successful";
    public static final String MSG_TOKEN_VALIDATED = "Token validated successfully";
    public static final String MSG_TOKEN_REFRESHED = "Token refreshed successfully";
    public static final String MSG_LOGOUT_SUCCESS = "Logout successful";

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(ApiResponse.success(MSG_AUTH_SUCCESS, authenticationService.authenticate(request)));
    }

    @PostMapping("/validate")
    ResponseEntity<ApiResponse<ValidateTokenResponse>> validate(@Valid @RequestBody TokenRequest request) throws ParseException, JOSEException {
        return ResponseEntity.ok(ApiResponse.success(MSG_TOKEN_VALIDATED, authenticationService.validateToken(request)));
    }

    @PostMapping("/refresh")
    ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@Valid @RequestBody TokenRequest request) throws ParseException, JOSEException {
        return ResponseEntity.ok(ApiResponse.success(MSG_TOKEN_REFRESHED, authenticationService.refreshToken(request)));
    }

    @PostMapping("/logout")
    ResponseEntity<ApiResponse<?>> logout(@Valid @RequestBody TokenRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ResponseEntity.ok(ApiResponse.success(MSG_LOGOUT_SUCCESS, null));
    }

    // TODO: add update authentication (username, password, email)

}
