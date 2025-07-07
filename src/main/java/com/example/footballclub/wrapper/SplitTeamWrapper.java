package com.example.footballclub.wrapper;

import com.example.footballclub.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SplitTeamWrapper {
    List<Player> players;
    boolean found;
    float totalElo;

    public SplitTeamWrapper setTotalElo(float totalElo) {
        this.totalElo = totalElo;
        return this;
    }

    public SplitTeamWrapper setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    public SplitTeamWrapper setFound(boolean found) {
        this.found = found;
        return this;
    }
}
