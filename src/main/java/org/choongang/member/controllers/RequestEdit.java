package org.choongang.member.controllers;

import lombok.Data;

@Data
public class RequestEdit {

  private String password;

  private String confirmPassword;

  private String tel;

  private String email;

}
