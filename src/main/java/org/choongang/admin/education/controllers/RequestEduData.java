package org.choongang.admin.education.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

import java.util.UUID;

@Data
public class RequestEduData {

    private Long num;
    private String mode = "add";
    private String gid = UUID.randomUUID().toString();

    @NotBlank
    private String name; // 학습자료명

    private String dataType; // 자료구분
    private String contentType; // 자료유형
    private String fileAddress; // 자료 file주소
    private String serviceType; // 서비스구분

    @NotBlank
    private String content; // 자료내용
    private FileInfo thumbnail;
}
