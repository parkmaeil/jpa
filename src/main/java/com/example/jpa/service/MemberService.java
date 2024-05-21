package com.example.jpa.service;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.Role;
import com.example.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 회원가입(패스워드 암호화, 권한부여)
    public Member register(Member member){
        // 1. 패스워드 암호화
        String hashedPwd=passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPwd);
        // 2. 권한부여
        Set<Role> roles=new HashSet<>();
        Role userRole=roleService.findByRole("USER");
        roles.add(userRole);
        /*
        Role managerRole=roleService.findByRole("MANAGER");
        roles.add(managerRole);
        Role adminRole=roleService.findByRole("ADMIN");
        roles.add(adminRole);
        */
        member.setRoles(roles);
        return memberRepository.save(member); // 가입
    }

}
