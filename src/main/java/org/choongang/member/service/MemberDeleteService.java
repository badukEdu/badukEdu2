package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDeleteService {

  private final MemberUtil memberUtil ;
  private final MemberRepository memberRepository ;

  /**
   * 회원 탈퇴 처리
   */
  public void deleteMember() {
    if (!memberUtil.isLogin()) {
      throw new UnAuthorizedException();
    }

    Member member = memberUtil.getMember() ;
    memberUtil.getMember().setEnable(false);
    memberRepository.saveAndFlush(member) ;
  }
}