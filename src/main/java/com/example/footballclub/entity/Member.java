package com.example.footballclub.entity;

import com.example.footballclub.enums.MemberRole;
import com.example.footballclub.enums.MemberStatus;
import com.example.footballclub.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Float elo;
    String name;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(100) default 'ACTIVE'")
    MemberStatus status = MemberStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    Organization organization;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    Set<Player> players;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    Set<Contest> createdContests;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(100) default 'MEMBER'")
    MemberRole role = MemberRole.MEMBER;

}
