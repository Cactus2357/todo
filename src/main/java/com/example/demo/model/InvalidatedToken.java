package com.example.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidatedToken {
    int tokenId;
    String jit;
    LocalDateTime expiry;
}

//token_id int auto_increment primary key,
//token_value varchar(255),
//expiry DATETIME

