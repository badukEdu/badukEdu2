package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FindIdValidator implements Validator {

    public final MemberRepository memberRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestFindId.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        // 이메일 + 회원명 조합으로 조회되는지 체크
        RequestFindId form = (RequestFindId) target;
        String email = form.email();
        String name = form.name();

        if (StringUtils.hasText(email) && StringUtils.hasText(name)
            && (!memberRepository.existsByEmailAndName(email,name))) {
            errors.reject("NotFound.member");
        }
    }
}
