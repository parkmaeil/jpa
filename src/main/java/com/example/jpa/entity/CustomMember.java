package com.example.jpa.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class CustomMember extends User { //  UserDetails-->User(username, password, 권한정보)

    private Member member;  // 이름, 전화번호, 주소, 이메일.....
    // String username, String password, Collection<? extends GrantedAuthority> authorities
    public CustomMember(Member member) {                     // Set<Role> -->Collection<GrantedAuthority>
        super(member.getUsername(), member.getPassword(), authorities);
        this.member=member;
    } 
    //     Set<Role> -->Collection<GrantedAuthority>

    // ---------------------------------------------------------
}
