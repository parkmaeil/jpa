package com.example.jpa.service;

import com.example.jpa.entity.CustomMember;
import com.example.jpa.entity.Member;
import com.example.jpa.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private MemberRepository memberRepository;
    @Override                                                        // username(bit, password(X)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername()~~");
        Optional<Member>  optional=memberRepository.findByUsername(username);
        if(!optional.isPresent()){
            throw new UsernameNotFoundException("user not found");
        }
        Member member=optional.get();
        return new CustomMember(member); // 회원아이디가 있으면 그 회원의 정보를 리턴-->패스워드비교 ?
        // UserDetails(interface)-->User(클래스, username, password, 권한정보)
    }
}
