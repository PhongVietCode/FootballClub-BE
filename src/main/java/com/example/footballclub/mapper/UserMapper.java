package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.UserCreateRequest;
import com.example.footballclub.dto.request.UserUpdateRequest;
import com.example.footballclub.dto.response.UserResponse;
import com.example.footballclub.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UserUpdateRequest request, @MappingTarget User user);

    UserResponse toUserResponse(User user);
}
