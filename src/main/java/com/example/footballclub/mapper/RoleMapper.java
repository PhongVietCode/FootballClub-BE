package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.RoleRequest;
import com.example.footballclub.dto.response.RoleResponse;
import com.example.footballclub.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
