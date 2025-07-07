package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.MemberUpdateRequest;
import com.example.footballclub.dto.request.OrganizationCreateRequest;
import com.example.footballclub.dto.request.OrganizationUpdateRequest;
import com.example.footballclub.dto.response.OrganizationDetailResponse;
import com.example.footballclub.dto.response.OrganizationResponse;
import com.example.footballclub.entity.Member;
import com.example.footballclub.entity.Organization;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    Organization toOrganization(OrganizationCreateRequest request);
    @Mapping(target = "member", ignore = true)
    OrganizationResponse toOrganizationResponse(Organization organization);
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    OrganizationDetailResponse toOrganizationDetailResponse(Organization organization);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(OrganizationUpdateRequest request, @MappingTarget Organization organization);

}
