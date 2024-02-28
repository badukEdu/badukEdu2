package org.choongang.guide.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.controllers.GameContentSearch;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/guide")
@RequiredArgsConstructor
public class GuideController implements ExceptionProcessor  {

    private final GameContentInfoService gameContentInfoService;

    @GetMapping
    public String index() {
        return "redirect:/guide/intro";
    }

    @GetMapping("/intro")
    public String intro(Model model) {
        commonProcess("intro", model);

        return "guide/intro";
    }
    @GetMapping("/use")
    public String use(Model model) {
        commonProcess("use", model);

        return "guide/use";
    }

    @GetMapping("/product")
    public String guide(@ModelAttribute GameContentSearch search, Model model) {
        commonProcess("product", model);

        // 상품 소개
        ListData<GameContent> data = gameContentInfoService.getList(search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());


        return "guide/product";
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
