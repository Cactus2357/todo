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
public class UpdateUserRequest {
    @Size(min = 1, max = 30, message = "display name must be between 3 and 30 characters")
    String displayName;

    @URL
    String avatar;

    @Max(value = 300, message = "description must not exceed 300 characters")
    String description;

    Integer status;
}
