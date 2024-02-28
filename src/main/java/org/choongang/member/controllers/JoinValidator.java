package org.choongang.member.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.validators.PasswordValidator;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, PasswordValidator {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 1. 이메일, 아이디 중복 여부 체크
         * 2. 비밀번호 복잡성 체크 - 대소문자 1개 각각 포함, 숫자 1개 이상 포함, 특수문자도 1개 이상 포함
         * 3. 비밀번호, 비밀번호 확인 일치 여부 체크
         * 4. 이메일 인증 여부 체크
         */

        RequestJoin form = (RequestJoin)target;

        String mode = form.getMode();
        if (mode.equals("step1")) {
            validateStep1(form, errors);
        } else if (mode.equals("step2")) {
            validateStep2(form, errors);
        }


    }

    private void validateStep1(RequestJoin form, Errors errors) {
        String email = form.getEmail();
        String name = form.getName();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotBlank");

        // 1. 이메일, 이름 중복 여부 체크
        if (StringUtils.hasText(email) && memberRepository.existsByEmail(email)) {
            errors.rejectValue("email", "Duplicated");
        }

        if (StringUtils.hasText(name) && memberRepository.existsByEmail(name)) {
            errors.rejectValue("name", "Duplicated");
        }

        // 4. 이메일 인증 여부 체크
        Boolean verified = (Boolean)session.getAttribute("EmailAuthVerified");
        if (verified == null || !verified) { // 이메일 인증 실패시
            errors.rejectValue("email", "NotVerified");
        }

        //필수 필드 NULL 여부
        if (form.getName() == null || form.getName().isEmpty()) {
            errors.rejectValue("name", "required", "이름을 입력하세요.");
        }
        if (!form.isAgree()) {
            errors.rejectValue("agree", "required", "이용 약관에 동의해주세요.");
        }
        if (!form.isAgree2()) {
            errors.rejectValue("agree2", "required", "개인정보 처리 및 이용에 동의해주세요.");
        }
        if (form.getEmail() == null || form.getEmail().isEmpty()) {
            errors.rejectValue("email", "required", "이메일을 입력하세요.");
        }


    }

    private void validateStep2(RequestJoin form, Errors errors) {
        String userId = form.getUserId();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        String birth = form.getBirth();
        String authority = form.getAuthority();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birth", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "authority", "NotBlank");

        if (StringUtils.hasText(userId) && memberRepository.existsByUserId(userId)) {
            errors.rejectValue("userId", "Duplicated");
        }

        // 2. 비밀번호 복잡성 체크 - 대소문자 1개 각각 포함, 숫자 1개 이상 포함, 특수문자도 1개 이상 포함
        if (StringUtils.hasText(password) &&
                (!alphaCheck(password, true) || !numberCheck(password) || !specialCharsCheck(password))) {
            errors.rejectValue("password", "Complexity");
        }

        // 3. 비밀번호, 비밀번호 확인 일치 여부 체크
        if (StringUtils.hasText(password) && StringUtils.hasText(confirmPassword)
                && !password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "Mismatch.password");
        }

    }
}
