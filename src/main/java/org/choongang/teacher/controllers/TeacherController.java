package org.choongang.teacher.controllers;

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
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    // 그룹 목록
    @GetMapping("/group")
    public String groupList(Model model) {
        commonProcess("list", model);

        return "teacher/group/list";
    }

    // 그룹 등록
    @GetMapping("/group/add")
    public String addGroup(Model model) {
        commonProcess("add", model);

        return "teacher/group/add";
    }

    @GetMapping("/group/edit/{num}")
    public String editGroup(@PathVariable("num") Long num, Model model) {
        commonProcess("edit", model);

        return "teacher/group/edit";
    }

    @PostMapping("/group/save")
    public String saveGroup(Model model) {

        return "redirect:/teacher/group"; // 추가, 수정 완료 후 그룹 목록으로 이동
    }

    @GetMapping("/group/accept")
    public String acceptGroup(Model model) {
        commonProcess("accept", model);

        return "teacher/group/accept";
    }

    @PostMapping("/group/accept")
    public String acceptGroupPs(Model model) {
        commonProcess("accept", model);

        return "redirect:/teacher/group/accept";
    }

    @GetMapping("/homework")
    public String homeworkList(Model model) {
        commonProcess("homework_list", model);

        return "teacher/homework/list";
    }

    // 숙제 생성
    @GetMapping("/homework/add")
    public String addHomework(Model model) {
        commonProcess("homework_add", model);

        return "teacher/homework/add";
    }

    // 숙제 배포
    @GetMapping("/homework/edit/{num}")
    public String editHomework(@PathVariable("num") Long num, Model model) {
        commonProcess("homework_edit", model);

        return "teacher/homework/edit";
    }

    // 숙제 생성 또는 수정 처리
    @PostMapping("/homework/save")
    public String saveHomework(Model model) {

        return "redirect:/teacher/homework";
    }

    @GetMapping("/homework/distribute")
    public String distributeHomework(Model model) {
        commonProcess("distribute", model);

        return "teacher/homework/distribute";
    }

    @PostMapping("/homework/distribute")
    public String distributeHomeworkPs(Model model) {
        commonProcess("distribute", model);

        return "redirect:/teacher/homework";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "교육자마당";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("add")) {
            pageTitle = "학습 그룹 등록::" + pageTitle;
        } else if (mode.equals("edit")) {
            pageTitle = "학습 그룹 수정::" + pageTitle;
        } else if (mode.equals("list")) {
            pageTitle = "학습 그룹 조회::" + pageTitle;
        } else if (mode.equals("homework_add")) {
            pageTitle = "숙제 생성::" + pageTitle;
        } else if (mode.equals("homework_edit")) {
            pageTitle = "숙제 수정::" + pageTitle;
        } else if (mode.equals("distribute")) {
            pageTitle = "숙제 배포::" + pageTitle;
        } else if (mode.equals("homework_list")) {
            pageTitle = "숙제 학습 진도 조회::" + pageTitle;
        } else if (mode.equals("accept")) {
            pageTitle = "회원 그룹 가입 승인::" + pageTitle;
        }

        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }
}
