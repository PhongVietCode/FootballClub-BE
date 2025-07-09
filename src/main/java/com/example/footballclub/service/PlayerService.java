package com.example.footballclub.service;

import com.example.footballclub.dto.request.PlayerCreateRequest;
import com.example.footballclub.dto.request.PlayerListRequest;
import com.example.footballclub.dto.response.PlayerResponse;
import com.example.footballclub.entity.*;
import com.example.footballclub.enums.MemberRole;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.PlayerMapper;
import com.example.footballclub.repository.ContestRepository;
import com.example.footballclub.repository.MemberRepository;
import com.example.footballclub.repository.OrganizationRepository;
import com.example.footballclub.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerService {
    PlayerRepository playerRepository;
    ContestRepository contestRepository;
    PlayerMapper playerMapper;
    MemberRepository memberRepository;

    public PlayerResponse joinContest(PlayerCreateRequest request) {
        Contest contest = contestRepository.findById(request.getContestId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));

        Member member = null;
        if (request.getMemberId() != null) {
            Optional<Player> player = playerRepository.findByContestIdAndMemberId(request.getContestId(), request.getMemberId());
            if (player.isPresent()) {
                throw new AppException(ErrorCode.MEMBER_JOINED_CONTEST);
            }
            member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_MEMBER));
        }
        Player player = playerMapper.toPlayer(request);
        player.setContest(contest);
        if (member != null) {
            player.setMember(member);
            player.setElo(member.getElo());
            player.setName(member.getName());
        } else {
            player.setElo(request.getElo());
        }
        player.setIsCustomer(request.getIsCustomer());
        player = playerRepository.save(player);
        return playerMapper.toPlayerResponse(player);
    }

    @Transactional
    public List<PlayerResponse> listPlayerToJoinContest(PlayerListRequest request) {
        Contest contest = contestRepository.findById(request.getContestId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));

        List<Member> memberList = memberRepository.findAllById(request.getMemberIds());
        List<Player> players = new ArrayList<>(contest.getPlayers());
        List<PlayerResponse> playerResponses = memberList.stream().map(member -> {
            if(!member.getRole().equals(MemberRole.MEMBER)){
                return null;
            }
            Optional<Player> existingPlayer = playerRepository.findByContestIdAndMemberId(request.getContestId(), member.getId());
            if (existingPlayer.isEmpty()) {
                Player player = Player.builder()
                        .contest(contest)
                        .elo(member.getElo())
                        .member(member)
                        .name(member.getName())
                        .build();
                player = playerRepository.save(player);
                return playerMapper.toPlayerResponse(player);
            } else {
                players.remove(existingPlayer.get());
                return null;
            }
        }).toList();
        playerRepository.deleteAllById(players.stream().map(Player::getId).toList());
        return playerResponses;
    }
}
