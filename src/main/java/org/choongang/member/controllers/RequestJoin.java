package org.choongang.member.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestJoin {

    private String gid = UUID.randomUUID().toString();

    private String mode = "step1";

    @Email
    private String email;

    @Size(min=6)
    private String userId;

    @Size(min=8)
    private String password;

    private String confirmPassword;

    private String name;

    private boolean agree;

}
