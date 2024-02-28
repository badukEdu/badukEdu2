package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.repositories.BoardRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.file.service.FileDeleteService;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KickService {

  private final MemberRepository memberRepository;
  private final MemberInfoService memberInfoService;

  /**
   * 회원 강퇴 삭제
   *
   * @param num :
   */
  public void delete(Long num) {

    memberRepository.deleteById(num);

    memberRepository.flush();
  }

  public void deleteList(List<Long> chks) {
    if (chks == null || chks.isEmpty()) {
      throw new AlertException("삭제할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
    }

    for (Long chk : chks) {
      //Long num = Long.valueOf(utils.getParam("num_" + chk));
      delete(chk);
    }
  }
}

