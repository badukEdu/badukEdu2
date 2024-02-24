package org.choongang.admin.order.service;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends AlertBackException {
    public OrderNotFoundException() {
        super("주문정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
