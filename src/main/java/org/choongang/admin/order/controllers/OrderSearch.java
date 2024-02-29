package org.choongang.admin.order.controllers;

import lombok.Data;
import org.choongang.admin.order.constants.PaymentMethod;
import org.choongang.admin.order.entities.OrderItem;
import org.choongang.member.entities.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderSearch {
    private int page = 1;
    private int limit = 20;

    private String orderName; // 구매자명
    private String orderMobile; // 연락처
    private Member member;
    private PaymentMethod paymentMethod = PaymentMethod.BANK_TRANSFER;
    private long totalPayment;
    private List<OrderItem> orderItems = new ArrayList<>();

    private String sopt;
    private String skey;

    private List<Long> orderNo;
}
