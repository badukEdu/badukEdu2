package org.choongang.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @ModelAttribute("pageTitle")
    public String pageTitle() {
        return "운영마당";
    }

    @GetMapping
    public String index() {
        return "admin/index";
    }
}
