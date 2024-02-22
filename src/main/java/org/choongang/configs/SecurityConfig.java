package org.choongang.configs;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.member.Authority;
import org.choongang.member.MemberUtil;
import org.choongang.member.service.LoginFailureHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.choongang.member.service.MemberInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final MemberInfoService memberInfoService;
  private final MemberUtil memberUtil;

  @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증 설정 S - 로그인, 로그아웃 */
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("userId")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailureHandler());
        });

        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");
        });
        /* 인증 설정 E - 로그인, 로그아웃 */
        
        /* 인가 설정 S - 접근 통제  작업 완료 후 // 제거 필수 !! */

//      http.authorizeHttpRequests(c -> {
//        c.requestMatchers("/member/**", "/admin/**").permitAll() //작업완료 후 "/admin/**" 제거
//            //.requestMatchers("/admin").hasAuthority("ADMIN") //작업완료 후 주석제거
//            .requestMatchers("/teacher").hasAnyAuthority("TEACHER", "ADMIN")
//            .requestMatchers("/student").hasAnyAuthority("STUDENT", "ADMIN")
//            .anyRequest().authenticated(); // 나머지는 회원 전용
//
//      });
//
//      http.exceptionHandling(c ->
//          // 미로그인시
//          c.authenticationEntryPoint((req, res, e) -> {
//                String URL = req.getRequestURI();
//                if (URL.indexOf("/admin") != -1) { // 관리자 페이지
//                  res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                } else { // 회원전용 페이지
//                  res.sendRedirect(req.getContextPath() + "/member/login");
//                }
//              })
//              // 로그인 후 권한 X
//              .accessDeniedHandler((req, res, e) -> {
//                String requestURL = req.getRequestURI();
//                if (requestURL.indexOf("/admin") != -1) {
//                  res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                } else {
//                  String redirectURL =  "/";
//                  if (memberUtil.isLogin()) {
//                    Authority authority = memberUtil.getMember().getAuthority();
//                    if (authority == Authority.TEACHER) {
//                      redirectURL =  "/teacher";
//                    } else if (authority == Authority.STUDENT) {
//                      redirectURL = "/student";
//                    }
//
//                  }
//
//                  res.sendRedirect(req.getContextPath() + redirectURL);
//                }
//              })
//      );

        /* http.authorizeHttpRequests(c -> {
           c.requestMatchers("/mypage/**").authenticated() // 회원 전용
                   //.requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "MANAGER")
                   .anyRequest().permitAll(); // 그외 모든 페이지는 모두 접근 가능
        });

        http.exceptionHandling(c -> {
           c.authenticationEntryPoint((req, res, e) -> {
                String URL = req.getRequestURI();
                if (URL.indexOf("/admin") != -1) { // 관리자 페이지
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else { // 회원전용 페이지
                    res.sendRedirect(req.getContextPath() + "/member/login");
                }

           });
        }); */

        /* 인가 설정 E - 접근 통제 */

        /* 자동 로그인 설정 S */
        http.rememberMe(c -> {
          c.rememberMeParameter("autoLogin") // 자동 로그인으로 사용할 요청 파리미터 명, 기본값은 remember-me
              .tokenValiditySeconds(60 * 60 * 24 * 30) // 로그인을 유지할 기간(30일로 설정), 기본값은 14일
              .userDetailsService(memberInfoService) // 재로그인을 하기 위해서 인증을 위한 필요 UserDetailsService 구현 객체
              .authenticationSuccessHandler(new LoginSuccessHandler()); // 자동 로그인 성공시 처리 Handler

        });
        /* 자동 로그인 설정 E */

        http.headers(c -> c.frameOptions(f -> f.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
