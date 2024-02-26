package org.choongang.member.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberUtil memberUtil;

    public LoginSuccessHandler(MemberUtil memberUtil) {
        this.memberUtil = memberUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        MemberUtil.clearLoginData(session);

        /* 회원 정보 조회 편의 구현 */
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        Member member = memberInfo.getMember();
        session.setAttribute("member", member);

        String redirectURL;
        if (memberUtil.isStudent()) {
            if (memberUtil.hasJoinStudyGroup()) {
                redirectURL = "/";
            } else {
                redirectURL = "/education/join";
            }
        } else {
            redirectURL = "/";
        }

        String redirectURLParam = request.getParameter("redirectURL");
        redirectURL = StringUtils.hasText(redirectURLParam) ? redirectURLParam : redirectURL;

        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}