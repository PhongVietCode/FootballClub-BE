package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.ContestCreateRequest;
import com.example.footballclub.dto.response.ContestResponse;
import com.example.footballclub.entity.Address;
import com.example.footballclub.entity.Contest;
import com.example.footballclub.entity.Member;
import com.example.footballclub.entity.Organization;
import com.example.footballclub.service.ContestService;
import com.sun.tools.jconsole.JConsoleContext;
import com.sun.tools.jconsole.JConsolePlugin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContestMapper {
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "players", ignore = true)
    Contest toContest(ContestCreateRequest request);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "addressName", ignore = true)
    ContestResponse toContestResponse(Contest contest);

    default ContestResponse mapToContestResponse(Contest contest) {
        Address address = contest.getAddress();
        ContestResponse contestResponse = this.toContestResponse(contest);
        contestResponse.setAddress(address.getAddress());
        contestResponse.setAddressName(address.getName());
        return contestResponse;
    }

    default Contest mapToContest(ContestCreateRequest request, Member member, Address address, Organization organization) {
        Contest contest = this.toContest(request);
        contest.setMember(member);
        contest.setAddress(address);
        contest.setOrganization(organization);
        return contest;
    }
}
