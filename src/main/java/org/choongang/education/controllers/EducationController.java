package org.choongang.education.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {
    
    // 신청 목록
    @GetMapping
    public String list(Model model) {
        commonProcess("list", model);

        return "education/list";
    }

    // 학습그룹 가입 신청
    @GetMapping("/join")
    public String join(Model model) {
        commonProcess("join", model);

        return "education/join";
    }

    // 신청 처리
    @PostMapping("/apply")
    public String apply(Model model) {
        commonProcess("join", model);

        return "redirect:/education";
    }

    // 신청 내용 취소
    @GetMapping("/cancel/{num}")
    public String cancel(@PathVariable("num") Long num, Model model) {
        commonProcess("cancel", model);

        return "redirect:/education";
    }

    // 신청 내용 상세
    @GetMapping("/group/{num}")
    public String view(@PathVariable("num") Long num, Model model) {
        commonProcess("view", model);

        return "education/view";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "학습서비스";
        List<String> addScript = new ArrayList<>();
        if (mode.equals("list")) {
            pageTitle = "신청목록::" + pageTitle;
        } else if (mode.equals("join")) {
            pageTitle = "학습그룹 가입신청::" + pageTitle;
            addScript.add("education/form");
        } else if (mode.equals("cancal")) {
            pageTitle = "신청취소::" + pageTitle;
        } else if (mode.equals("view")) {
            pageTitle = "신청내용::" + pageTitle;
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", "education_" + mode);
    }
}
