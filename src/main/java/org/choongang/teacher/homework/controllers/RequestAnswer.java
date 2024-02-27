package org.choongang.teacher.homework.controllers;

import lombok.Data;

@Data
public class RequestAnswer {

    private Long num;

    private String questionAnswer;

    private Long score;
}
