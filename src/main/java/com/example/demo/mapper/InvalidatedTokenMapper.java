package com.example.demo.mapper;

import com.example.demo.model.InvalidatedToken;

public interface InvalidatedTokenMapper {
    int insertToken(InvalidatedToken token);

    int countInvalidatedToken(String jit);
}
