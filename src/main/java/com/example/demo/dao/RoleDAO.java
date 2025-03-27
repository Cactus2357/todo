package com.example.demo.dao;

import com.example.demo.mapper.RoleMapper;
import com.example.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAO {
    private final RoleMapper roleMapper;

    public RoleDAO(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public List<Role> getRolesByUserId(int userId) {
        return roleMapper.getRolesByUserId(userId);
    }

    public Role getRoleById(int roleId) {
        return roleMapper.getRoleById(roleId);
    }

    public int updateUserRole(int userId, int ...roleIds) {
        int result = 0;
        result += roleMapper.deleteUserRole(userId);
        result += roleMapper.insertUserRole(userId, roleIds);
        return result;
    }

}
