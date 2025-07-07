package com.example.footballclub.entity;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "full_name")
    String fullName;
    String password;
    @Column(name = "email", nullable = true)
    String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    Set<Member> members;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(100) default 'ACTIVE'")
    UserStatus status = UserStatus.ACTIVE;
}
