package org.choongang.teacher.group.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RequestStGroup {

    private Long num;    //기본키

    @NotBlank
    private String name;     //스터디그룹명

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; //시작일

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; //종료일

    @NotNull
    private Long maxSubscriber;    //최대인원

    @NotNull
    private Long maxLevel;   //달성 레벨
    private String text;     //비고
    private String mode = "add";    //수정 , 생성
    private long gameContentNum;    //게임 컨텐츠 번호

    @NotNull
    private long month;
    private String teacherName;

    private String gameTitle;
    private Long memberNum;

}
