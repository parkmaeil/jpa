package com.example.jpa.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomMember extends User { //  UserDetails-->User(username, password, authorities)

    private Member member;  // 이름, 전화번호, 주소, 이메일.....
    // String username, String password, Collection<? extends GrantedAuthority> authorities
    public CustomMember(Member member) {                     // Set<Role> -->Collection<GrantedAuthority>
        super(member.getUsername(), member.getPassword(), getToGrantedAuthority(member.getRoles())); // USER -> [ROLE_USER.ROLE_MANAGER]
        this.member=member;
    } 
    //     Set<Role> -->Collection<GrantedAuthority> --> SimpleGrantedAuthority
    private static Collection<? extends  GrantedAuthority> getToGrantedAuthority(Set<Role> roles){ // authorities
        //             USER(Role), MANAGER(Role)  -> [ROLE_USER, ROLE_MANAGER]
        return roles.stream()
                .map(role->new SimpleGrantedAuthority("ROLE_"+role.getName()))
                .collect(Collectors.toList());
    }
    // ---------------------------------------------------------
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }
}
