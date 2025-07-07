package com.example.footballclub.repository;

import com.example.footballclub.entity.Member;
import com.example.footballclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query("SELECT m FROM Member m JOIN m.createdContests c WHERE c.id = :contestId AND m.organization.id = :organizationId")
    Member findByContestIdAndOrganizationId(@Param("contestId") String contestId, @Param("organizationId") String organizationId);
    @Query("SELECT m FROM Member m JOIN m.user u WHERE u.id = :userId AND m.organization.id = :organizationId")
    Optional<Member> findByUserIdAndOrganizationId(@Param("userId") String userId, @Param("organizationId") String organizationId);
    Optional<Member> findByName(String name);
}
