package com.example.jpa.config;

import com.example.jpa.jwt.JwtAuthenticationFilter;
import com.example.jpa.jwt.JwtAuthorizationFilter;
import com.example.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final MemberRepository memberRepository;
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
        /*
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
        */
         http
                 .formLogin(form->form.disable())
                 .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .httpBasic(hb->hb.disable())
                 .csrf(csrf->csrf.disable())
                 .authorizeHttpRequests(authz->authz                        // new SecurityContextHolder(세센)
                         .requestMatchers("/jwt/v1/user/**").hasAnyRole("USER","MANAGER","ADMIN")
                         .requestMatchers("/jwt/v1/manager/**").hasAnyRole("MANAGER","ADMIN")
                         .requestMatchers("/jwt/v1/admin/**").hasAnyRole("ADMIN")
                 )
                 .apply(new MyCustomDsl());

             return http.build();
    }
     // 인증에 필요한 필터를 동작시키기 위해서 사용자정의  클래스를 등록(DSL) : [AuthenticationManager 객체]
     // /login --> UsernamePasswordAuthenticationFilter
     public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity>{
         @Override
         public void configure(HttpSecurity http) throws Exception {
             // AuthenticationManager 얻어오기
             AuthenticationManager authenticationManager=http.getSharedObject(AuthenticationManager.class);
             // UsernamePasswordAuthenticationFilter 실행할수있다. - 내일 -
             // /login -> UsernamePasswordAuthenticationFilter -extends ->  ?
             http
                     .addFilter(new JwtAuthenticationFilter(authenticationManager))
                     .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository));
         }
     }
}
