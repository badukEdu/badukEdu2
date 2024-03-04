package org.choongang.admin.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.member.Authority;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAuthorityService {
    private final Utils utils;
    private final MemberRepository memberRepository;

    public void saveList(List<Long> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (Long chk : chks) {

            String num = utils.getParam("num_" + chk);

            Member member = memberRepository.findById(Long.valueOf(num)).orElse(null);

            if (member == null) continue;

            String authorityValue = utils.getParam("authority_" + num);

            member.setAuthority(Authority.valueOf(authorityValue));

            memberRepository.save(member);
        }
        memberRepository.flush();
    }
}
