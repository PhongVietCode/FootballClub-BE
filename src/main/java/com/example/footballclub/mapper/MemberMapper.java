package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.MemberCreateRequest;
import com.example.footballclub.dto.request.MemberUpdateRequest;
import com.example.footballclub.dto.response.MemberResponse;
import com.example.footballclub.entity.Member;
import com.example.footballclub.enums.MemberStatus;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.decorator.MemberDecorator;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapper(componentModel = "spring")
@DecoratedWith(MemberDecorator.class)
public interface MemberMapper {

    @Mapping(target = "organization", ignore = true)
    Member toMember(MemberCreateRequest request);

    @Mapping(target = "organizationId", ignore = true)
    MemberResponse toMemberResponse(Member member);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(MemberUpdateRequest request, @MappingTarget Member member);

    default MemberStatus mapStatus(String status) {
        if (status == null) return MemberStatus.ACTIVE;
        try {
            return MemberStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
