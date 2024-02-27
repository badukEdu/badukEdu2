package org.choongang.member.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestEdit {
  @NotBlank
  private String password;

  private String confirmPassword;

  private String tel;

  private String email;

}
