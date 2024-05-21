package com.example.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    // 1.  비밀번호 암호화 빈설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 회원가입시 -> 비밀번호 암호화
    }
    // 2 . SecurityFilterChain -> 인증매니저(관리) -> UserDetailsService(DB연동 서비스 클래스)-> 구현체
   // http://localhost:8081/ui/~~
    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //  HttpSecurity -> 설정~~~
        // 1. 인증,권한 설정
            http.authorizeHttpRequests(authz->authz
                    .requestMatchers("/api/**").authenticated()
                    .requestMatchers("/book/**").authenticated()
                    //.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                   .anyRequest().permitAll()
            )
          // 2. 로그인 폼   @Controller
                    .formLogin(form->form
                            .loginPage("/ui/list") // <form action="/login"
                            .loginProcessingUrl("/login") //-> 스프링 시큐리티 동작(username, password)->DB연동->.......
                            // UsernamePasswordAuthenticationFilter 동작 -> UserDetailsService의 메서드를 실행(loadUserByUsername)

                            .defaultSuccessUrl("/ui/list", true)
                    )
        // 3. 로그아웃
                    .logout(logout->logout
                            .logoutUrl("/logout") // <form action="/logout" method="post">, <a href="/logout">logout</a>
                            .logoutSuccessUrl("/ui/list")
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                            .clearAuthentication(true) // SecurityContextHolder(세션)
                            .deleteCookies("JSESSIONID")
                            .invalidateHttpSession(true)
                    );

        return http.build();
    }

}
