package org.choongang.guide.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/guide")
@RequiredArgsConstructor
public class GuideController {

    @GetMapping
    public String index() {
        return "redirect:/guide/intro";
    }

    @GetMapping("/{mode}")
    public String guide(@PathVariable("mode") String mode, Model model) {
        commonProcess(mode, model);

        return "guide/" + mode;
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "intro";
        List<String> addCss = new ArrayList<>();
        addCss.add("guide/" + mode);
        String pageTitle = "이용안내";
        if (mode.equals("intro")) pageTitle = "사이트 소개::" + pageTitle;
        if (mode.equals("use")) pageTitle = "이용 가이드::" + pageTitle;
        if (mode.equals("product")) pageTitle = "상품 소개::" + pageTitle;
        if (mode.equals("notice&faq")) pageTitle = "Notice & QnA::" + pageTitle;
        if (mode.equals("qna")) pageTitle = "Q&A::" + pageTitle;

        model.addAttribute("addCss", addCss);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }
}
