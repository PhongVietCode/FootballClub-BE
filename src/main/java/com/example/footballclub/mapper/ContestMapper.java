package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.ContestCreateRequest;
import com.example.footballclub.dto.response.ContestResponse;
import com.example.footballclub.entity.Contest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContestMapper {
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "players", ignore = true)
    Contest toContest(ContestCreateRequest request);
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "addressName", ignore = true)
    ContestResponse toContestResponse(Contest contest);
}
