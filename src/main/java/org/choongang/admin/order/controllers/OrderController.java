package org.choongang.admin.order.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.order.entities.OrderItem;
import org.choongang.admin.order.service.OrderInfoService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminOrderController")
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class OrderController implements ExceptionProcessor  {

    private final OrderInfoService orderInfoService;

    /**
     * 주문 목록
     *
     * @param search
     * @param model
     * @return
     */
    @GetMapping
    public String list(@ModelAttribute OrderSearch search, Model model) {
        commonProcess("list", model);

        ListData<OrderItem> data = orderInfoService.getList(search, true);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "admin/order/list";

    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "운영마당";
        List<String> addScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = "매출 조회::" + pageTitle;
        }


        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", "order_" + mode);
    }
}
