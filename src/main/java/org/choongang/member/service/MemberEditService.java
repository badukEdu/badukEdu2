package org.choongang.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.controllers.RequestEdit;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MemberEditService {

  private final MemberRepository memberRepository;
  private final MemberUtil memberUtil;
  private final PasswordEncoder encoder;
  private final HttpSession session;

  /**
   * 회원정보 수정 처리
   * @param form
   */
  public void edit(RequestEdit form) {
    Member member = memberUtil.getMember();
    member.setTel(form.getTel());
    member.setEmail(form.getEmail());

    String password = form.getPassword();
    if (StringUtils.hasText(password)) {
      member.setPassword(encoder.encode(password.trim()));
    }

    memberRepository.saveAndFlush(member);

    session.setAttribute("member", member);
  }
}
