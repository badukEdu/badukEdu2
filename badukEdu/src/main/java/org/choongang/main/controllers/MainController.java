package org.choongang.main.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {

    @GetMapping("/")
    public String index() {

        return "main/index";
    }
}

