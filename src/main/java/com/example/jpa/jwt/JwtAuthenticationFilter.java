package com.example.jpa.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jpa.entity.CustomMember;
import com.example.jpa.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

//                                                                                /login ->username, password
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                                                                                                                        throws AuthenticationException {
        // /login -> JSON -> username, password -> Member Object
        // ObjectMapper --------------------------|
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            Member member=objectMapper.readValue(request.getInputStream(), Member.class);
            System.out.println(member); // toString()
            UsernamePasswordAuthenticationToken authenticationToken=
                    new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
            Authentication authentication =authenticationManager.authenticate(authenticationToken); // loadUserByUsername()
            CustomMember customMember=(CustomMember)authentication.getPrincipal();
            System.out.println(customMember.getMember().getUname()); // 이름
            return authentication; // SecurityContextHolder(세션) -Authentication(principal)
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication()");
        // JWT 토큰 만들어서 ---> 응답
        CustomMember customMember=(CustomMember)authResult.getPrincipal();
        // jwtToken =>header+payload+sign : jwt.io
       String jwtToken= JWT.create()
               .withSubject("JWT토큰")
               .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) // 10분
               .withClaim("username", customMember.getMember().getUsername())
               .sign(Algorithm.HMAC256("cosin"));

       // 요청 클라이어트의 해더에 응답을 해주기....
        response.addHeader("Authorization","Bearer " + jwtToken); // 응답
    }
}
