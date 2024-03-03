package org.choongang.admin.member.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.member.entities.Member;
import org.choongang.member.service.KickService;
import org.choongang.member.service.MemberInfoService;
import org.choongang.menus.Menu;
import org.choongang.menus.MenuDetail;
import org.choongang.statistic.controllers.RequestSearch;
import org.choongang.statistic.service.MemberStatisticService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    private final MemberInfoService memberInfoService;
    private final KickService kickService;
    private final MemberStatisticService statisticService;
    private final ObjectMapper objectMapper;


    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "member";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {

        return Menu.getMenus("member");
    }

    /**
     * 회원 목록 조회 검색
     * @param search
     * @param model
     * @return
     */
    @GetMapping
    public String list(@ModelAttribute  MemberSearch search, Model model) {
        commonProcess("list", model);

        ListData<Member> data = memberInfoService.getList(search);

        model.addAttribute("items", data.getItems()); // 목록
        model.addAttribute("pagination", data.getPagination()); // 페이징

        return "admin/member/list";
    }

    @DeleteMapping
    public String deleteList(@RequestParam(name = "chk") List<Long> chks, Model model) {
        commonProcess("list", model);
        kickService.deleteList(chks);
        return "redirect:/admin/member";
    }

//    @PostMapping("/lock")
//    public String lockMembers(@RequestParam("memberIds") String[] memberIds, Model model) {
//        commonProcess("list", model);
//        memberInfoService.lockMembers(memberIds);
//        return "redirect:/admin/member";
//    }


    @GetMapping("/stat")
    public String statistic(@Valid @ModelAttribute RequestSearch search, Errors erros, Model model) {
        commonProcess("statistic", model);

        LocalDate sDate = Objects.requireNonNullElse(search.getSDate(), LocalDate.now().minusWeeks(1));
        search.setSDate(sDate);

        Map<String, Long> data = statisticService.getData(search);

        try {
            String jsonData = objectMapper.writeValueAsString(data);
            model.addAttribute("json", jsonData);

        } catch (JsonProcessingException e) {

        }

        return "admin/member/stat";
    }


    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "운영마당";
        List<String> addScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = "회원 관리::" + pageTitle;
        } else if (mode.equals("statistic")) {
            addScript.add("common/chart");
            addScript.add("admin/member/stat");

        }


        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", "member_" + mode);
    }
}
