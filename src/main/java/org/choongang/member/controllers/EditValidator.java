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
public class EditValidator implements Validator, PasswordValidator {

  private final MemberRepository memberRepository;
  private final HttpSession session;

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.isAssignableFrom(RequestEdit.class);
  }

  @Override
  public void validate(Object target, Errors errors) {
    RequestEdit form = (RequestEdit) target;

    String tel = form.getTel();
    String email = form.getEmail();
    String password = form.getPassword();
    String confirmPassword = form.getConfirmPassword();

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "비밀번호가 공백입니다.");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "field.required", "비밀번호 확인이 공백입니다.");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tel", "field.required", "전화번호가 공백입니다.");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "이메일이 공백입니다.");

    if (StringUtils.hasText(password)) {

      if (!alphaCheck(password, false) || !numberCheck(password) || !specialCharsCheck(password)) {
        errors.rejectValue("password", "Complexity");
      }

      if (!StringUtils.hasText(confirmPassword)) {
        errors.rejectValue("confirmPassword", "NotBlank");
      }

      if (!password.equals(confirmPassword)) {
        errors.rejectValue("confirmPassword", "Mismatch.password");
      }

      if (StringUtils.hasText(email) && memberRepository.existsByEmail(email)) { //중복 체크
        errors.rejectValue("email", "Duplicated");
      }

      if (!StringUtils.hasText(tel)) { //공백여부
        errors.rejectValue("Tel", "NotBlank");
      }

      // 4. 이메일 인증 여부 체크
      Boolean verified = (Boolean)session.getAttribute("EmailAuthVerified");
      if (verified == null || !verified) { // 이메일 인증 실패시
        errors.rejectValue("email", "NotVerified");
      }

    }
  }
}
