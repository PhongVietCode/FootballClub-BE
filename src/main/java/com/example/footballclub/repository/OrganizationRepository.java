package com.example.footballclub.repository;

import com.example.footballclub.entity.Organization;
import com.example.footballclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface OrganizationRepository extends JpaRepository<Organization, String> {
    Optional<Organization> findByName(String name);
}
