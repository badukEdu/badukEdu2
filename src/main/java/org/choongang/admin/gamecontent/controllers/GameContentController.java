package org.choongang.admin.gamecontent.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.service.GameContentDeleteService;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.admin.gamecontent.service.GameContentSaveService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("adminGameContentController")
@RequestMapping("/admin/gamecontent")
@RequiredArgsConstructor
public class GameContentController implements ExceptionProcessor  {

    private final GameContentInfoService gameContentInfoService;
    private final GameContentSaveService gameContentSaveService;
    private final GameContentDeleteService gameContentDeleteService;

    @GetMapping
    public String list(@ModelAttribute GameContentSearch search, Model model) {
        commonProcess("list", model);

        ListData<GameContent> data = gameContentInfoService.getList(search, true);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "admin/gamecontent/list";
    }

    /**
     * 게임 컨텐츠 등록
     *
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(@ModelAttribute RequestGameContentData form, Model model) {
        commonProcess("add", model);

        return "admin/gamecontent/add";
    }

    /**
     * 게임 컨텐츠 수정
     * 
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/edit/{num}")
    public String edit(@PathVariable("num") Long num, Model model) {
        commonProcess("edit", model);

        RequestGameContentData form = gameContentInfoService.getForm(num);
        model.addAttribute("requestGameContentData", form);

        return "admin/gamecontent/edit";
    }

    /**
     * 게임 컨텐츠 삭제
     *
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/delete/{num}")
    public String delete(@PathVariable("num") Long num, Model model) {
        gameContentDeleteService.delete(num);

        return "redirect:/admin/gamecontent";
    }

    @PostMapping("/save")
    public String save(@Valid RequestGameContentData form, Errors errors, Model model) {
        String mode = form.getMode();
        commonProcess(mode, model);

        if(errors.hasErrors()) {
            return "admin/gamecontent/" + mode;
        }

        gameContentSaveService.save(form);

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
            addScript.add("fileManager");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", "gamecontent_" + mode);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
    }
}
