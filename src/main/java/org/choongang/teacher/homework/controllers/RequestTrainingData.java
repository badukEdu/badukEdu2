package org.choongang.teacher.homework.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestTrainingData { // 학습자 - 숙제 제출시 사용

    private Long num;

    @NotBlank
    private String homeworkAnswer; // 숙제 답변

    private String question; // 질문

}
