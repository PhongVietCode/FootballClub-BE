package com.example.footballclub.service;

import com.example.footballclub.dto.request.MemberCreateRequest;
import com.example.footballclub.dto.request.MemberQueryRequest;
import com.example.footballclub.dto.request.MemberUpdateRequest;
import com.example.footballclub.dto.response.MemberResponse;
import com.example.footballclub.entity.*;
import com.example.footballclub.enums.MemberRole;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.MemberMapper;
import com.example.footballclub.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberService {

    MemberRepository memberRepository;
    OrganizationRepository organizationRepository;
    RoleRepository roleRepository;
    MemberMapper memberMapper;
    UserRepository userRepository;
    ContestRepository contestRepository;

    public MemberResponse createMember(MemberCreateRequest request) {
        Organization organization = organizationRepository.findById(
                        request.getOrganizationId()
                )
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_ORGANIZATION));
        Member member = memberMapper.toMember(request);
        if (request.getUserId() != null) {
            Optional<User> user = userRepository.findById(request.getUserId());
            if (user.isPresent()) {
                member.setUser(user.get());
            }
        }
        member.setOrganization(organization);
        member = memberRepository.save(member);
        return memberMapper.toMemberResponse(member);
    }

    //    TODO: role admin
    public MemberResponse updateMember(String id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_MEMBER));
        memberMapper.update(request, member);
        member.setRole(MemberRole.valueOf(request.getRole()));
        memberRepository.save(member);
        return memberMapper.toMemberResponse(member);
    }

    @Transactional
    public List<MemberResponse> getMemberList(MemberQueryRequest request) {
        List<Member> members = new ArrayList<>();

        if (request.getOrgId() != null) {
            Organization organization = organizationRepository.findById(request.getOrgId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_ORGANIZATION));
            members = new ArrayList<>(organization.getMembers());
        }
        if (request.getContestId() != null) {
            Contest contest = contestRepository.findById(request.getContestId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));
            members = new ArrayList<>(contest.getPlayers().stream().map(Player::getMember).toList());
        }
        if (request.getRole() != null) {
            members = members.stream().filter(member -> member.getRole().equals(MemberRole.valueOf(request.getRole()))).toList();
        }
        if (request.getName() != null) {
            members = members.stream().filter(member -> {
                if (member.getName() != null) {
                    return member.getName().startsWith(request.getName());
                }
                return false;
            }).toList();
        }
        return members.stream().map(memberMapper::toMemberResponse).toList();
    }

}
