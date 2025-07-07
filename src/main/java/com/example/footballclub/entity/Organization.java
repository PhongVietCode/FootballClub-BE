package com.example.footballclub.entity;

import com.example.footballclub.enums.OrganizationStatus;
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
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "name", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String name;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    User admin;

    @Column(name = "logo_url")
    String logoUrl;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    Set<Member> members;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    Set<Address> addresses;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    Set<Contest> contests;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(100) default 'ACTIVE'")
    OrganizationStatus status = OrganizationStatus.ACTIVE;
}
