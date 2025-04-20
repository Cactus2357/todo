package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotBlank(message = "username is required")
    @Pattern(regexp = "^\\w{3,20}$", message = "username must be 3-20 characters and can only contain letters, numbers, and underscores")
    String username;

    @NotBlank(message = "email address is required")
    @Email
    String email;

    @NotBlank(message = "password is required")
    @Min(value = 8, message = "password must be at least 8 characters")
    String password;

    @Size(min = 1, max = 30, message = "display name must be between 3 and 30 characters")
    String displayName;

    @URL
    String avatar;
}
