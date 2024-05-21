package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 1

    @Column(length = 50, nullable = false, unique = true)
    private String username; // 아이디
    private String password; // 패스워드(암호화)
    private String uname; // 사용자이름
    private String email; // 이메일

    @ManyToMany(fetch = FetchType.EAGER)  // M:N : 다대다 관계
    @JoinTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "member_id"),  // 1
            inverseJoinColumns = @JoinColumn(name="role_id") // 1
    )
    // 권한(여러개)
    private Set<Role> roles;  // [USER, MANAGER]  Role 1 - USER / 2 - MANAGER / 3 - ADMIN
}
/*
          M              :             N
       회원                           권한( 1 : USER, 2: MANAGER, 3: ADMIN, 4: ~~~)
    1   bitcocom                 USER, MANAGER
    2  cocom                      MANAGER
    3  bit                            ADMIN

              member_roles(관계테이블)
      X      (FK                   FK) : PK
       1       1                    1(USER)
       2       1                    2(MANAGER)
       3       2                    1(USER)
       4       2                   2(MANAGER)
       5       2                   3(ADMIN)

        고객                          제품
          1                                 1     우유
          2                                 2    라면
          3                                 3   콜라
                  구매(관계테이블) : 행위-비즈니스관계
       PK         [FK                FK] :    수량
        1            1                   1 : 우유         2
        2           2                  3
        3          1                  1
        4

         학생                          과목

                   수강(관계테이블) : 행위-비즈니스관계
      AK(대체키)         [FK             FK]:PK                 일자
             1                       1                1           2024-2
             2                       1                2
             3                       2                1
             4                       2                3
             5                       1                 1

*/
