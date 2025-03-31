package com.example.demo.mapper;

import com.example.demo.model.Role;

import java.util.List;

public interface RoleMapper {
    List<Role> getAllRoles();

    List<Role> getRolesByUserId(int userId);

    Role getRoleById(int roleId);

    Role getRoleByName(String roleName);

    int insertRole(Role role);

    int insertUserRole(int userId, int[] roleIds);

    int deleteUserRole(int userId);
}
