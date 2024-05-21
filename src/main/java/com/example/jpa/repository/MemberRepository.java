package com.example.jpa.repository;

import com.example.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {
    // save()
    public Optional<Member> findByUsername(String username);
}
