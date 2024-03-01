package org.choongang.admin.order.controllers;

import lombok.Data;

import java.util.List;

@Data
public class OrderSearch {
    private int page = 1;
    private int limit = 20;

    private String sopt;
    private String skey;

    private List<Long> orderNo;
}
