package org.choongang.member.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestEdit {
  @NotBlank
  private String password;

  @NotBlank
  private String confirmPassword;

  @Size(min=10, max=11)
  private String tel;

  @NotBlank
  private String email;

}
