package org.choongang.admin.education.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.education.entities.EduData;
import org.choongang.admin.education.service.EduContentDeleteService;
import org.choongang.admin.education.service.EduDataInfoService;
import org.choongang.admin.education.service.EduDataSaveService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("adminEducationController")
@RequestMapping("/admin/education")
@RequiredArgsConstructor
public class EducationController implements ExceptionProcessor  {

    private final EduDataSaveService eduDataSaveService;
    private final EduDataInfoService eduDataInfoService;
    private final EduContentDeleteService eduContentDeleteService;

    @GetMapping
    public String list(@ModelAttribute EduDataSearch search, Model model) {
        commonProcess("list", model);

        ListData<EduData> data = eduDataInfoService.getList(search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "admin/education/list";
    }

    /**
     * 교육 자료 등록
     *
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(@ModelAttribute RequestEduData form, Model model) {
        commonProcess("add", model);

        return "admin/education/add";
    }

    /**
     * 교육 자료 수정
     *
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/edit/{num}")
    public String edit(@PathVariable("num") Long num, Model model) {
        commonProcess("edit", model);

        RequestEduData form = eduDataInfoService.getForm(num);
        model.addAttribute("requestEduData", form);

        return "admin/education/edit";
    }

    /**
     * 교육 자료 삭제
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/delete/{num}")
    public String delete(@PathVariable("num") Long num, Model model) {
        eduContentDeleteService.delete(num);

        return "redirect:/admin/education";
    }

    @PostMapping("/save")
    public String save(@Valid RequestEduData form, Errors errors, Model model) {
        String mode = form.getMode();
        commonProcess(mode, model);

        if(errors.hasErrors()) {
            return "admin/education/" + mode;
        }

        eduDataSaveService.save(form);

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
            addScript.add("fileManager");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
    }
}
