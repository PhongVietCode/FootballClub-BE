package com.example.footballclub.mapper;

import com.example.footballclub.dto.request.PlayerCreateRequest;
import com.example.footballclub.dto.response.PlayerResponse;
import com.example.footballclub.entity.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    Player toPlayer(PlayerCreateRequest request);
    PlayerResponse toPlayerResponse(Player player);

}
