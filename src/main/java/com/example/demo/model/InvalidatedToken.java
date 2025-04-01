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
    int jitId;
    String jit;
    LocalDateTime expiry;
}

//jit_id int auto_increment primary key,
//jit varchar(255),
//expiry DATETIME

