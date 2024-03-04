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
import org.choongang.member.entities.Member;
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

        Member member = memberUtil.getMember();

        Long orderNo = System.currentTimeMillis();
        OrderInfo orderInfo = OrderInfo.builder()
                .orderNo(orderNo)
                .orderMobile(form.getOrderMobile())
                .orderName(form.getOrderName())
                .member(member)
                .paymentMethod(PaymentMethod.valueOf(form.getPaymentMethod()))
                .totalPayment(totalPayment)
                .build();

        orderInfoRepository.saveAndFlush(orderInfo);

        List<OrderItem> orderItems = new ArrayList<>();
        for (GameContent item : items) {

            OrderItem orderItem = orderItemRepository.getMemberItem(member, item).orElse(null);

            LocalDate startDate = null, endDate = null;

            if (orderItem != null) { // 기 구독 상품이 있는 경우
                startDate = orderItem.getStartDate();
                endDate = orderItem.getEndDate().plusMonths(item.getSubscriptionMonths());
                orderItem.setSubscriptionMonths(orderItem.getSubscriptionMonths() + item.getSubscriptionMonths());
            } else {
                startDate = LocalDate.now();
                endDate = startDate.plusMonths(item.getSubscriptionMonths());
                orderItem = new OrderItem();
                orderItem.setOrderInfo(orderInfo);
                orderItem.setGameContent(item);
                orderItem.setSubscriptionMonths(item.getSubscriptionMonths());
            }

            orderItem.setGameTitle(item.getGameTitle());
            orderItem.setSalePrice(item.getSalePrice());
            orderItem.setStartDate(startDate);
            orderItem.setEndDate(endDate);
            orderItem.setTotalPayment(orderItem.getTotalPayment() + item.getSalePrice());
            orderItem.setOrderName(orderInfo.getOrderName());


            orderItems.add(orderItem);
        }

        orderItemRepository.saveAllAndFlush(orderItems);

        return orderInfo;
    }
}
