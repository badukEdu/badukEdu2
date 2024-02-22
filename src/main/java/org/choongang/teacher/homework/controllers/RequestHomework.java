package org.choongang.teacher.homework.controllers;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestHomework {
    private String mode = "add";
    private Long num;

    @NotBlank
    private String name;
    @NotBlank @Lob
    private String content;

    private Long memberNum;

    @NotNull
    @Min(1) @Max(100)
    private Long studyLevel;
    @NotNull
    private LocalDate deadLine;
}
