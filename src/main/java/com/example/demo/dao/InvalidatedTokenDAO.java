package com.example.demo.dao;

import com.example.demo.mapper.InvalidatedTokenMapper;
import com.example.demo.model.InvalidatedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InvalidatedTokenDAO {
    private final InvalidatedTokenMapper invalidatedTokenMapper;

    public InvalidatedTokenDAO(InvalidatedTokenMapper invalidatedTokenMapper) {
        this.invalidatedTokenMapper = invalidatedTokenMapper;
    }

    public int invalidateToken(InvalidatedToken invalidatedToken) {
        return invalidatedTokenMapper.insertToken(invalidatedToken);
    }

    public boolean isInvalidated(String jit) {
        return invalidatedTokenMapper.countInvalidatedToken(jit) > 0;
    }
}
