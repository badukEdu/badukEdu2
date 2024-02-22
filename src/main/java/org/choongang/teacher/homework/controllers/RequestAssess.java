package org.choongang.teacher.homework.controllers;

import lombok.Data;

@Data
public class RequestAssess { // 교육자 - 숙제 평가시 사용

    private String questionAnswer; // 질문 답변

    private Long score; // 점수
}
