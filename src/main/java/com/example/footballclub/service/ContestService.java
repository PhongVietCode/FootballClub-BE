package com.example.footballclub.service;


import com.example.footballclub.dto.request.ContestCreateRequest;
import com.example.footballclub.dto.request.ContestUpdateRequest;
import com.example.footballclub.dto.response.ContestResponse;
import com.example.footballclub.dto.response.TeamSplitResponse;
import com.example.footballclub.entity.*;
import com.example.footballclub.enums.MemberRole;
import com.example.footballclub.enums.MemberStatus;
import com.example.footballclub.enums.PlayerStatus;
import com.example.footballclub.enums.TeamColor;
import com.example.footballclub.exception.AppException;
import com.example.footballclub.exception.ErrorCode;
import com.example.footballclub.mapper.ContestMapper;
import com.example.footballclub.mapper.PlayerMapper;
import com.example.footballclub.mapper.TeamMapper;
import com.example.footballclub.repository.*;
import com.example.footballclub.wrapper.SplitTeamWrapper;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContestService {
    ContestRepository contestRepository;
    TeamRepository teamRepository;
    OrganizationRepository organizationRepository;
    MemberRepository memberRepository;
    PlayerRepository playerRepository;
    AddressRepository addressRepository;
    ContestMapper contestMapper;
    PlayerMapper playerMapper;

    @Transactional
    public ContestResponse createContest(ContestCreateRequest request) {
        Address address = addressRepository.findById(request.getAddressId()).orElseThrow(() -> new AppException(ErrorCode.INVALID_ADDRESS));
        Optional<Member> member = memberRepository.findById(request.getMemberId());
        if (member.isEmpty() || !member.get().getStatus().equals(MemberStatus.ACTIVE)) {
            throw new AppException(ErrorCode.INVALID_MEMBER);
        }
        if (member.get().getRole().equals(MemberRole.MEMBER)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        Contest contest = contestMapper.mapToContest(request, member.get(), address, member.get().getOrganization());
        if (request.getPlayers() != null) {
            List<Player> players = playerRepository.findAllById(request.getPlayers());
            contest.setPlayers(players);
        }
        contest = contestRepository.save(contest);
        return contestMapper.mapToContestResponse(contest);
    }

//    TODO: make sure updater is valid ???
    public ContestResponse updateContest(String contestId, ContestUpdateRequest request) {
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));
        List<Team> teams = new ArrayList<>();
        var splittedTeams = request.getTeamSplitted();

        for (var team : splittedTeams) {
            List<Player> players = playerRepository.findAllById(team.getPlayerIds());
            Team newTeam = Team.builder().contest(contest).color(team.getColor()).totalElo(team.getTotalElo()).players(new HashSet<>(players)).build();
            newTeam = teamRepository.save(newTeam);
            for (Player player : players) {
                player.setTeam(newTeam);
            }
            teams.add(newTeam);
        }
        contest.setTeams(new HashSet<>(teams));
        contest = contestRepository.save(contest);
        return contestMapper.toContestResponse(contest);
    }

    public List<TeamSplitResponse> splitTeam(String id) {
        Contest contest = contestRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));
//        Get active players
        List<Player> players = new ArrayList<>(contest.getPlayers().stream().filter(player -> player.getStatus().equals(PlayerStatus.ACTIVE)).toList());
        Collections.shuffle(players);
        if (players.size() % contest.getTeamCount() != 0) {
            throw new AppException(ErrorCode.TEAM_COUNT_IS_NOT_VALID);
        }
        int teamCount = contest.getTeamCount();
        float totalElo = 0;
        for (Player p : players) {
            totalElo += p.getElo();
        }
        int minElo = (int) totalElo / teamCount;
        int maxElo = minElo + (int) totalElo % teamCount;
        int teamSize = players.size() / teamCount;
        int tryCount = 10;
        List<TeamSplitResponse> responses = List.of();
        while (tryCount > 0) {
            boolean isFailed = false;
            List<SplitTeamWrapper> splittedTeam = new ArrayList<>();
            for (int i = 0; i < teamCount; i++) {
                SplitTeamWrapper result = new SplitTeamWrapper();
                helper(new ArrayList<>(), players, 0, 0, minElo, maxElo, teamSize, 0, result);
                if (result.getPlayers() != null) {
                    splittedTeam.add(result);
                    players.removeAll(result.getPlayers());
                } else {
                    isFailed = true;
                    break;
                }
            }
            if (isFailed) {
                Collections.shuffle(players);
                tryCount--;
                continue;
            }
            responses = new ArrayList<>();
            int index = 0;
            for (var res : splittedTeam) {
                responses.add(TeamSplitResponse.builder().color(TeamColor.values()[index++].toString()).players(res.getPlayers().stream().map(playerMapper::toPlayerResponse).toList()).totalElo(res.getTotalElo()).build());
            }
            break;
        }
        return responses;

    }

    private void helper(List<Player> playersInTeam, List<Player> players, int index, float curElo, int minElo, int maxElo, int teamSize, int gap, SplitTeamWrapper result) {
        if (playersInTeam.size() == teamSize) {
            if (curElo >= minElo && curElo <= maxElo) {
                if (!result.isFound()) {
                    result.setPlayers(new ArrayList<>(playersInTeam));
                    result.setFound(true);
                    result.setTotalElo(curElo);
                    return;
                }
            }
        }
        if (index == players.size()) {
            return;
        }
        playersInTeam.add(players.get(index));
        helper(playersInTeam, players, index + 1, curElo + players.get(index).getElo(), minElo, maxElo, teamSize, gap, result);
        playersInTeam.removeLast();
        helper(playersInTeam, players, index + 1, curElo, minElo, maxElo, teamSize, gap, result);
    }

    public List<ContestResponse> getContestList(String orgId) {
        Organization organization = organizationRepository.findById(orgId).orElseThrow(() -> new AppException(ErrorCode.INVALID_ORGANIZATION));
        return organization.getContests().stream().sorted(Comparator.comparing(Contest::getDateTime).reversed()).map(contestMapper::mapToContestResponse).toList();
    }

    @Transactional
    public ContestResponse getContestDetail(String contestId) {
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));
        ContestResponse contestResponse = contestMapper.mapToContestResponse(contest);
        List<Team> teams = new ArrayList<>(contest.getTeams());
        if (!teams.isEmpty()) {
            List<TeamSplitResponse> teamSplitResponses = new ArrayList<>();
            teams.forEach(team -> {
                teamSplitResponses.add(TeamSplitResponse.builder().color(team.getColor()).players(team.getPlayers().stream().map(playerMapper::toPlayerResponse).toList()).totalElo(team.getTotalElo()).build());
            });
            contestResponse.setTeams(teamSplitResponses);
        }
        return contestResponse;
    }

    @Transactional
    public void deleteContest(String contestId) {
//        Delete players -> Delete team -> Delete contest
        try {
            Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new AppException(ErrorCode.INVALID_CONTEST));
            List<Player> players = contest.getPlayers();
            playerRepository.deleteAllById(players.stream().map(Player::getId).toList());
            List<Team> teams = contest.getTeams().stream().toList();
            teamRepository.deleteAllById(teams.stream().map(Team::getId).toList());
            contestRepository.deleteById(contestId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }
}
