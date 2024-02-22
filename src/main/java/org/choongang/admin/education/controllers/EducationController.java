package org.choongang.admin.education.controllers;

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

@Controller("adminEducationController")
@RequestMapping("/admin/education")
@RequiredArgsConstructor
public class EducationController {

    @GetMapping
    public String list(Model model) {
        commonProcess("list", model);

        return "admin/education/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        commonProcess("add", model);

        return "admin/education/add";
    }

    @GetMapping("/edit/{num}")
    public String edit(@PathVariable("num") Long num, Model model) {
        commonProcess("edit", model);

        return "admin/education/edit";
    }

    @PostMapping("/save")
    public String save(Model model) {

        return "redirect:/admin/education";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "운영마당";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = "교육 자료 조회::" + pageTitle;
        } else if (mode.equals("add") || mode.equals("edit")) {
            pageTitle = "교육 자료 " + (mode == "edit" ? "수정":"등록") + " ::" + pageTitle;
            addScript.add("admin/education/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
    }
}
