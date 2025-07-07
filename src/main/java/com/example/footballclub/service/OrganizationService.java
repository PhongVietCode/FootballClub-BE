package com.example.footballclub.service;


import com.example.footballclub.dto.request.OrganizationCreateRequest;
import com.example.footballclub.dto.request.OrganizationUpdateRequest;
import com.example.footballclub.dto.response.AddressResponse;
import com.example.footballclub.dto.response.OrganizationDetailResponse;
import com.example.footballclub.dto.response.OrganizationResponse;
import com.example.footballclub.entity.Member;
import com.example.footballclub.entity.Organization;
import com.example.footballclub.entity.User;
import com.example.footballclub.enums.MemberRole;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.AddressMapper;
import com.example.footballclub.mapper.MemberMapper;
import com.example.footballclub.mapper.OrganizationMapper;
import com.example.footballclub.repository.MemberRepository;
import com.example.footballclub.repository.OrganizationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationService {
    OrganizationRepository organizationRepository;
    MemberRepository memberRepository;
    OrganizationMapper organizationMapper;
    MemberMapper memberMapper;
    AddressMapper addressMapper;

    public OrganizationResponse create(OrganizationCreateRequest request) {

        Optional<Organization> existingOrg = organizationRepository.findByName(request.getName());
        if (existingOrg.isPresent()) {
            throw new AppException(ErrorCode.DUPLICATED_ORG_NAME);
        }
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Create organization
        Organization organization = organizationMapper.toOrganization(request);
        organization.setAdmin(user);
        organization = organizationRepository.save(organization);
//        Create admin for the organization
        Member member = Member.builder()
                .elo(0f)
                .user(user)
                .role(MemberRole.ADMIN)
//                .name(user.getFullName())
                .organization(organization)
                .build();
        memberRepository.save(member);
        OrganizationResponse response = organizationMapper.toOrganizationResponse(organization);
        response.setMember(memberMapper.toMemberResponse(member));
        return response;
    }

    public OrganizationResponse updateOrganization(String id, OrganizationUpdateRequest request) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_ORGANIZATION));
        organizationMapper.update(request, organization);
        organizationRepository.save(organization);
        return organizationMapper.toOrganizationResponse(organization);
    }

    public OrganizationDetailResponse getDetail(String id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_ORGANIZATION));
        OrganizationDetailResponse response = organizationMapper.toOrganizationDetailResponse(organization);
        Set<Member> members = new HashSet<>(organization.getMembers());
        response.setMembers(members.stream().map(memberMapper::toMemberResponse).toList());
        response.setAddresses(organization.getAddresses().stream().map(addressMapper::toAddressResponse).toList());
        return response;
    }
}
