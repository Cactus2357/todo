package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 1, max = 30, message = "display name must be between 3 and 30 characters")
    String displayName;

    @URL(message = "avatar must be a valid URL")
    String avatar;

    @Size(max = 300, message = "description must not exceed 300 characters")
    String description;

    @NotNull(message = "status is required")
    Integer status;
}
