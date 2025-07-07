package com.example.footballclub.repository;

import com.example.footballclub.entity.Role;
import com.example.footballclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
