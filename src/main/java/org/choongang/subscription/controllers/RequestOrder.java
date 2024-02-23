package org.choongang.subscription.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.admin.order.constants.PaymentMethod;

import java.util.List;

@Data
public class RequestOrder {
    private List<Long> num;

    @NotBlank
    private String orderName;

    @NotBlank
    private String orderMobile;

    @NotBlank
    private String paymentMethod = PaymentMethod.BANK_TRANSFER.name();

    private String depositor;
}
