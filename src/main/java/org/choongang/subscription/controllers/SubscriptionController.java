package org.choongang.subscription.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.controllers.GameContentSearch;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.commons.ListData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final GameContentInfoService gameContentInfoService;

    /* 구매한 게임콘텐츠 목록 */
    @GetMapping
    public String list(@ModelAttribute GameContentSearch search,
                       Model model) {
        commonProcess("list", model);

        ListData<GameContent> data = gameContentInfoService.getList(search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());;

        return "subscription/list";
    }

    /* 게임컨텐츠 구매 할 수 있는 리스트*/
    @GetMapping("/subscribe")
    public String subscribe(@ModelAttribute GameContent form,
                            GameContentSearch search,
                            Model model) {
        commonProcess("apply", model);

        ListData<GameContent> data = gameContentInfoService.getList(search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "subscription/subscribe";
    }

    /* 게임컨텐츠 결제 */
    @PostMapping("/apply")
    public String subscribePs(@ModelAttribute GameContentSearch search, Model model,
                              @RequestParam(name = "chk") List<Long> chks) {
        commonProcess("apply", model);

        List<GameContent> items = new ArrayList<>();
        Long totalMoney =0L;
        for(Long num : chks){
            items.add(gameContentInfoService.getById(num));
            totalMoney += gameContentInfoService.getById(num).getSalePrice();
        }

        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("items", items);

        return "subscription/list";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "구독서비스";

        List<String> addScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = "내 게임콘텐츠 조회::" + pageTitle;
        } else if (mode.equals("apply")) {
            pageTitle = "게임콘텐츠 구매::" + pageTitle;
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
    }
}
