package com.example.footballclub.service;

import com.example.footballclub.dto.request.PlayerCreateRequest;
import com.example.footballclub.dto.request.PlayerListRequest;
import com.example.footballclub.dto.response.PlayerResponse;
import com.example.footballclub.entity.*;
import com.example.footballclub.enums.PlayerStatus;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerService {
    PlayerRepository playerRepository;
    ContestRepository contestRepository;
    OrganizationRepository organizationRepository;
    PlayerMapper playerMapper;
    MemberRepository memberRepository;

    public PlayerResponse joinContest(PlayerCreateRequest request) {
        Contest contest = contestRepository.findById(request.getContestId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));

        Optional<Member> member = Optional.empty();
        if (request.getMemberId() != null) {
            member = memberRepository.findById(request.getMemberId());
        }
        Player player = playerMapper.toPlayer(request);
        player.setContest(contest);
        if (member.isPresent()) {
            player.setMember(member.get());
            player.setElo(member.get().getElo());
            player.setName(member.get().getName());
        } else {
            player.setElo(request.getElo());
        }
        player.setIsCustomer(request.getIsCustomer());
        player = playerRepository.save(player);
        return playerMapper.toPlayerResponse(player);
    }

    @Transactional
    public List<PlayerResponse> listToJoinContest(PlayerListRequest request) {
        Contest contest = contestRepository.findById(request.getContestId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));

        List<Member> memberList = memberRepository.findAllById(request.getMemberIds());
        return memberList.stream().map(member -> {
            Player player = Player.builder()
                    .contest(contest)
                    .elo(member.getElo())
                    .member(member)
                    .name(member.getName())
                    .build();
            player = playerRepository.save(player);
            return playerMapper.toPlayerResponse(player);
        }).toList();
    }
}
