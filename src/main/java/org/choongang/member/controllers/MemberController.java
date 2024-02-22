package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes({"requestJoin"})
public class MemberController implements ExceptionProcessor {

    private final JoinService joinService;
    private final JoinValidator joinValidator;

    @GetMapping
    public String join() {
        return "redirect:/member/join";
    }

    @GetMapping("/join/step1")
    public String joinStep1(@ModelAttribute RequestJoin form, Model model) {
        commonProcess("join", model);

        return "member/join_step1";
    }

    @PostMapping("/join/step2")
    public String joinStep2(@Valid RequestJoin form, Errors errors, Model model) {
        commonProcess("join", model);

        form.setMode("step1");

        joinValidator.validate(form, errors);

        if (errors.hasErrors()) {
            return "member/join_step1";
        }

        return "member/join_step2";
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors,Model model, SessionStatus status) {
        commonProcess("join", model);
        form.setMode("step2");


        joinService.process(form, errors);

        if (errors.hasErrors()) {
            return "member/join_step2";
        }

        status.setComplete(); // 세션 비우기

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess("login", model);

        return "member/login";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");

        List<String> addScript = new ArrayList<>();

        if (mode.equals("login")) {
            pageTitle = Utils.getMessage("로그인", "commons");

        } else if (mode.equals("join")) {
            addScript.add("fileManager");
            addScript.add("member/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
    }
}