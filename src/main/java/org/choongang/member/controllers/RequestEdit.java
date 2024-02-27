package org.choongang.member.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestEdit {
  @NotBlank
  private String password;
  @NotBlank
  private String confirmPassword;
  @NotBlank
  private String tel;
  @NotBlank
  private String email;

}
