package org.choongang.admin.member.controllers;

import lombok.Data;
import org.choongang.member.Authority;

@Data
public class MemberSearch {
    private int page = 1;
    private int limit = 20;

    private String userId;
    private String name;
    private String tel;
    private String email;
    private Authority authority;

    private String sopt;
    private String skey;
}
