package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.ContestCreateRequest;
import com.example.footballclub.dto.response.ContestResponse;
import com.example.footballclub.dto.response.TeamResponse;
import com.example.footballclub.entity.Contest;
import com.example.footballclub.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    @Mapping(target = "players", ignore = true)
    TeamResponse toTeamResponse(Team team);
}
