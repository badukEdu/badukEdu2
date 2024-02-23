package org.choongang.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.admin.gamecontent.service.GameContentNotFoundException;
import org.choongang.admin.order.constants.PaymentMethod;
import org.choongang.admin.order.entities.OrderInfo;
import org.choongang.admin.order.entities.OrderItem;
import org.choongang.admin.order.repositories.OrderInfoRepository;
import org.choongang.admin.order.repositories.OrderItemRepository;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.member.MemberUtil;
import org.choongang.subscription.controllers.RequestOrder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderApplyService {
    private final MemberUtil memberUtil;
    private final OrderInfoRepository orderInfoRepository;
    private final OrderItemRepository orderItemRepository;
    private final GameContentInfoService gameContentInfoService;

    public OrderInfo apply(RequestOrder form) {
        List<Long> nums = form.getNum();
        if (nums == null || nums.isEmpty()) {
            throw new AlertException("주문할 상품이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> data = gameContentInfoService.getOrderSummary(nums);

        List<GameContent> items = (List<GameContent>)data.get("items");
        long totalPayment = (long)data.get("totalPayment");
        if (items == null || items.isEmpty()) {
            throw new GameContentNotFoundException();
        }
        Long orderNo = System.currentTimeMillis();
        OrderInfo orderInfo = OrderInfo.builder()
                .orderNo(orderNo)
                .orderMobile(form.getOrderMobile())
                .orderName(form.getOrderName())
                .member(memberUtil.getMember())
                .paymentMethod(PaymentMethod.valueOf(form.getPaymentMethod()))
                .totalPayment(totalPayment)
                .build();

        orderInfoRepository.saveAndFlush(orderInfo);

        List<OrderItem> orderItems = new ArrayList<>();
        for (GameContent item : items) {
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusMonths(item.getSubscriptionMonths());
            OrderItem orderItem = OrderItem.builder()
                    .orderInfo(orderInfo)
                    .gameContent(item)
                    .gameTitle(item.getGameTitle())
                    .salePrice(item.getSalePrice())
                    .startDate(startDate)
                    .endDate(endDate)
                    .subscriptionMonths(item.getSubscriptionMonths())
                    .build();
            orderItems.add(orderItem);
        }

        orderItemRepository.saveAllAndFlush(orderItems);

        return orderInfo;
    }
}
