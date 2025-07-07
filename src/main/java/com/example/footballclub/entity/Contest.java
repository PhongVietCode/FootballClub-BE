package com.example.footballclub.entity;


import com.example.footballclub.enums.ContestStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "creater_id", referencedColumnName = "id", nullable = false)
    Member member;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    Organization organization;
    Integer teamCount;
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    Address address;

    @Builder.Default
    ContestStatus status = ContestStatus.ACTIVE;

    @OneToMany(mappedBy = "contest", fetch = FetchType.LAZY)
    List<Player> players;

    @OneToMany(mappedBy = "contest", fetch = FetchType.LAZY)
    Set<Team> teams;
    @Builder.Default
    @Column(name = "date_time", columnDefinition = "timestamp default current_timestamp")
    LocalDateTime dateTime = LocalDateTime.now();
}
