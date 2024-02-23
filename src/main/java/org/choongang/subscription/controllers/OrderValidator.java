package org.choongang.subscription.controllers;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestOrder.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestOrder form = (RequestOrder) target;

        String paymentMethod = form.getPaymentMethod();
        String depositor = form.getDepositor();
        if (StringUtils.hasText(paymentMethod) && paymentMethod.equals("BANK_TRANSFER") && !StringUtils.hasText(depositor)) {
            errors.rejectValue("depositor", "NotBlank");
        }
    }
}
