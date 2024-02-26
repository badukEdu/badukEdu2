package org.choongang.teacher.homework.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestQuestionAnswer {

    private Long num;

    @NotBlank
    private String QuestionAnswer;
}
