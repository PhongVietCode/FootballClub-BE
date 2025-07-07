package com.example.footballclub.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "contest_id", referencedColumnName = "id", nullable = false)
    Contest contest;
    String color;
    Float totalElo;
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    Set<Player> players;
}
