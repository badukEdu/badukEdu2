package org.choongang.subscription.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    /* 구매한 게임콘텐츠 목록 */
    @GetMapping
    public String list(Model model) {
        commonProcess("list", model);

        return "subscription/list";
    }

    /* 게임컨텐츠 구매 할 수 있는 리스트*/
    @GetMapping("/subscribe")
    public String subscribe(Model model) {
        commonProcess("apply", model);

        return "subscription/subscribe";
    }

    @PostMapping("/subscribe/apply")
    public String subscribePs(Model model) {
        commonProcess("apply", model);

        return "redirect:/subscription";
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
