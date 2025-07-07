package com.example.footballclub.entity;


import com.example.footballclub.enums.PlayerStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Builder.Default
    @Column(name = "elo", columnDefinition = "varchar(100) default 0.0")
    Float elo = 0.0f;
    @Builder.Default
    @Column(name = "paid", columnDefinition = "boolean default false")
    Boolean paid = false;
    String name;
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = true)
    Member member;
    @ManyToOne
    @JoinColumn(name = "contest_id", referencedColumnName = "id", nullable = false)
    Contest contest;
    @Column(name = "is_customer", columnDefinition = "boolean default false")
    Boolean isCustomer;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(100) default 'ACTIVE'")
    PlayerStatus status = PlayerStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    Team team;
}
