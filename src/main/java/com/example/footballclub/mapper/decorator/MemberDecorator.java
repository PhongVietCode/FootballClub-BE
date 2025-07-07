package com.example.footballclub.mapper.decorator;


import com.example.footballclub.dto.request.MemberCreateRequest;
import com.example.footballclub.dto.response.MemberResponse;
import com.example.footballclub.entity.Member;
import com.example.footballclub.entity.Organization;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.MemberMapper;
import com.example.footballclub.repository.MemberRepository;
import com.example.footballclub.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

public abstract class MemberDecorator implements MemberMapper {
    @Autowired
    @Qualifier("delegate")
    private MemberMapper delegate;

    @Autowired
    OrganizationRepository organizationRepository;
/*

    @Override
    public Member toMember(MemberCreateRequest request) {
        Member member = delegate.toMember(request);
        Organization organization = organizationRepository.findById(request.getOrganizationId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_ORGANIZATION));
        member.setOrganization(organization);
        return member;
    }

    @Override
    public MemberResponse toMemberResponse(Member member) {
        MemberResponse memberResponse = delegate.toMemberResponse(member);
        memberResponse.setOrganizationId(member.getOrganization().getId());
        return memberResponse;
    }
*/
}
