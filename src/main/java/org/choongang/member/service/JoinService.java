package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.Authority;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final MemberRepository memberRepository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;


    public void process(RequestJoin form, Errors errors) {
        validator.validate(form, errors);
        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

        Authority authority = StringUtils.hasText(form.getAuthority()) ?
            Authority.valueOf(form.getAuthority()) : Authority.USER;

        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setPassword(hash);
        member.setGender(form.getGender());
        member.setUserId(form.getUserId());
        member.setTel(form.getTel());
        member.setBirth(form.getBirth());
        member.setSMSAgree(form.isSMSAgree());
        member.setEmailAgree(form.isEmailAgree());
        member.setAgree(form.isAgree());
        member.setAgree2(form.isAgree2());
        member.setAgree3(form.isAgree3());
        member.setAgree4(form.isAgree4());
        member.setAgree5(form.isAgree5());
        member.setAuthority(authority);

        process(member);

    }

    public void process(Member member) {
        memberRepository.saveAndFlush(member);
    }
}
