package org.choongang.admin.gamecontent.controllers;

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

@Controller("adminGameContentController")
@RequestMapping("/admin/gamecontent")
@RequiredArgsConstructor
public class GameContentController {

    @GetMapping
    public String list(Model model) {
        commonProcess("list", model);

        return "admin/gamecontent/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        commonProcess("add", model);

        return "admin/gamecontent/add";
    }

    @GetMapping("/edit/{num}")
    public String edit(@PathVariable("num") Long num, Model model) {
        commonProcess("edit", model);

        return "admin/gamecontent/edit";
    }

    @PostMapping("/save")
    public String save(Model model) {

        return "redirect:/admin/gamecontent";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "운영마당";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = "게임콘텐츠 조회::" + pageTitle;
        } else if (mode.equals("add") || mode.equals("edit")) {
            pageTitle = "게임콘텐츠 " + (mode == "edit" ? "수정":"등록") + " ::" + pageTitle;
            addScript.add("admin/gamecontent/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", "gamecontent_" + mode);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
    }
}
