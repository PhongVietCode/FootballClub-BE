package com.example.footballclub.service;

import com.example.footballclub.dto.request.UserCreateRequest;
import com.example.footballclub.dto.request.UserUpdateRequest;
import com.example.footballclub.dto.response.MemberProfileResponse;
import com.example.footballclub.dto.response.OrganizationResponse;
import com.example.footballclub.dto.response.UserProfileResponse;
import com.example.footballclub.dto.response.UserResponse;
import com.example.footballclub.entity.Member;
import com.example.footballclub.entity.Organization;
import com.example.footballclub.entity.User;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.MemberMapper;
import com.example.footballclub.mapper.OrganizationMapper;
import com.example.footballclub.mapper.UserMapper;
import com.example.footballclub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    OrganizationMapper organizationMapper;
    private final MemberMapper memberMapper;

    public UserResponse create(UserCreateRequest userCreateRequest) {
        Optional<User> existingUser = userRepository.findByFullName(userCreateRequest.getFullName());
        if (existingUser.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userCreateRequest);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_USER));
        userMapper.update(request, user);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_USER));
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserProfileResponse getProfile() {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(current.getId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_USER));
        List<OrganizationResponse> organizations = new ArrayList<>();
        user.getMembers().forEach(member -> {
            OrganizationResponse orgResponse = organizationMapper.toOrganizationResponse(member.getOrganization());
            orgResponse.setMember(memberMapper.toMemberResponse(member));
            organizations.add(orgResponse);
        });

        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getFullName())
                .organizations(organizations)
                .build();

    }
//    TODO: forgot/change password
//    TODO: delete user

}
