package org.choongang.admin.gamecontent.controllers;

import lombok.Data;

@Data
public class GameContentSearch {
    private int page = 1;
    private int limit = 10;
    private String sopt = "ALL"; // 검색옵션
    private String skey = ""; // 검색 키워드

}
