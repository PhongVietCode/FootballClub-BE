package com.example.footballclub.repository;

import com.example.footballclub.entity.Player;
import com.example.footballclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PlayerRepository extends JpaRepository<Player, String> {
}
