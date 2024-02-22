package org.choongang.education.group.controllers;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestJoinStGroup {


    private Long num;
    private LocalDateTime SDate; // 신청일 (자동생성)
    private boolean accept = false; // 가입승인여부
    private LocalDateTime JoinDate; // 가입 승인일
    private List<Long> studyGroupNum; // 스터디그룹
    private Long memberNum; //구독회원 회원번호

}

