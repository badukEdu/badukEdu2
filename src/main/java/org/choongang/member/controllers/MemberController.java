package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.service.FindIdService;
import org.choongang.member.service.FindPwService;
import org.choongang.member.service.JoinService;
import org.choongang.menus.Menu;
import org.choongang.menus.MenuDetail;
import org.choongang.member.service.MemberEditService;
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
@SessionAttributes({"EmailAuthVerified", "requestJoin"})
public class MemberController implements ExceptionProcessor {

    private final JoinService joinService;
    private final JoinValidator joinValidator;
    private final FindPwService findPwService;
    private final FindIdService findIdService;
    private final FindIdValidator findIdValidator;
    private final MemberUtil memberUtil;
    private final MemberEditValidator memberEditValidator;
    private final MemberEditService memberEditService;

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return Menu.getMenus("guide");
    }

    @ModelAttribute("requestJoin")
    public RequestJoin requestJoin() {
        return new RequestJoin();
    }

    @GetMapping
    public String join() {
        return "redirect:/member/join";
    }

    @GetMapping("/join/step1")
    public String joinStep1(@ModelAttribute RequestJoin form, Model model) {
        commonProcess("join", model);

        // 이메일 인증 여부 false로 초기화
        model.addAttribute("EmailAuthVerified", false);

        return "member/join/step1";
    }

    @PostMapping("/join/step2")
    public String joinStep2(@Valid RequestJoin form, Errors errors, Model model) {
        commonProcess("join", model);

        form.setMode("step1");

        joinValidator.validate(form, errors);

        if (errors.hasErrors()) {
            System.out.println(errors + "step1@@@@@@@@@@@@@@@@@@@@@@@");

            return "member/join/step1";
        }

        return "member/join/step2";
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model, SessionStatus status) {
        commonProcess("join", model);
        form.setMode("step2");


        joinService.process(form, errors);

        if (errors.hasErrors()) {
            System.out.println(errors + "step2@@@@@@@@@@@@@@@@@@@@@@@");

            return "member/join/step2";
        }
        // EmailAuthVerified, requestJoin 세션값 비우기 */
        status.setComplete();

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute RequestJoin form, Model model) {
        commonProcess("login", model);

        return "member/login";
    }

    @PostMapping("/login")
    public String loginPs(@Valid RequestJoin form, Errors errors, Model model) {
        commonProcess("login", model);

        return "redirect:/studyGroup";
    }

    /**
     * 회원 정보 수정
     */

    @ModelAttribute("requestEdit")
    public RequestEdit requestEdit() {
        return new RequestEdit();
    }

    @GetMapping("/member_edit")
    public String editMemberInfo(@ModelAttribute RequestEdit form, Model model) {
        // 회원 정보 수정 로직 추가
        commonProcess("member_edit", model);
        Member member = (Member) memberUtil.getMember();

            model.addAttribute("member", member);
            model.addAttribute("EmailAuthVerified", false);

            return "member/member_edit";
    }

    @PostMapping("/member_edit")
    public String editMemberInfoPs(@ModelAttribute RequestEdit form, Errors errors, Model model) {

        memberEditValidator.validate(form, errors);

        if (errors.hasErrors()) {
            System.out.println(errors + "member_edit@@@@@@@@@@@@@@@@@@@@@@@");

            return "member/member_edit";
        }
        memberEditService.edit(form);

        return "redirect:/";
    }

    /* 아이디 찾기 S */

    /**
     * 아이디 찾기 양식
     *
     * @param form
     * @param model
     * @return
     */

    @GetMapping("/find_id")
    public String findId(@ModelAttribute RequestFindId form, Model model) {
        commonProcess("find_id", model);

        return "member/find_id";
    }

    /**
     * 아이디 찾기 처리
     *
     * @param form
     * @param errors
     * @param model
     * @return
     */

    @PostMapping("/find_id")
    public String findIdPs(@Valid RequestFindId form, Errors errors, Model model) {
        commonProcess("find_id", model);

        findIdValidator.validate(form, errors);

        if (errors.hasErrors()) {

            return "member/find_id";
        }

        findIdService.sendUserId(form.name(), form.email());

        return "member/find_id_done";
    }


    @GetMapping("/find_id_done")

    public String toFindIdDone() {

        return "member/find_id_done";
    }

    @PostMapping("/member/find_id_done")
    public String findIdDone() {

        return "member/find_id_done";
    }

    /* 아이디 찾기 E */

    /**
     * 비밀번호 찾기 양식
     *
     * @param model
     * @return
     */
    @GetMapping("/find_pw")
    public String findPw(@ModelAttribute RequestFindPw form, Model model) {
        commonProcess("find_pw", model);

        return "member/find_pw";
    }

    /**
     * 비밀번호 찾기 처리
     *
     * @param model
     * @return
     */
    @PostMapping("/find_pw")
    public String findPwPs(@Valid RequestFindPw form, Errors errors, Model model) {
        commonProcess("find_pw", model);

        findPwService.process(form, errors); // 비밀번호 찾기 처리

        if (errors.hasErrors()) {
            return "member/find_pw";
        }

        // 비밀번호 찾기에 이상 없다면 완료 페이지로 이동
        return "redirect:/member/find_pw_done";
    }

    /**
     * 비밀번호 찾기 완료 페이지
     *
     * @param model
     * @return
     */
    @GetMapping("/find_pw_done")
    public String findPwDone(Model model) {
        commonProcess("find_pw", model);

        return "member/find_pw_done";
    }


    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");

        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();

        if (mode.equals("login")) {
            pageTitle = Utils.getMessage("로그인", "commons");

        } else if (mode.equals("join")) {
            addScript.add("member/form");

        } else if (mode.equals("find_id")) {
            pageTitle = Utils.getMessage("아이디_찾기", "commons");

        } else if (mode.equals("find_pw")) {
            pageTitle = Utils.getMessage("비밀번호_찾기", "commons");

        } else if (mode.equals("member_edit")) {
            addScript.add("member/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addCommonScript", addCommonScript);

    }
}