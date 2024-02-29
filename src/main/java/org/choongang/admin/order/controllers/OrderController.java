package org.choongang.admin.order.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.order.entities.OrderItem;
import org.choongang.admin.order.service.OrderInfoService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.statistic.controllers.RequestSearch;
import org.choongang.statistic.service.MemberStatisticService;
import org.choongang.statistic.service.OrderStatisticService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller("adminOrderController")
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class OrderController implements ExceptionProcessor  {

    private final OrderInfoService orderInfoService;
    private final OrderStatisticService statisticService;
    private final ObjectMapper objectMapper;


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

        return "admin/order/stat";
    }


    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "운영마당";
        List<String> addScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = "매출 조회::" + pageTitle;
        } else if (mode.equals("statistic")) {
        addScript.add("common/chart");
        addScript.add("admin/order/stat");

    }



        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", "order_" + mode);
    }
}
