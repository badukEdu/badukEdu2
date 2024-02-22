package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class AlertRedirectException extends AlertException {

    private String redirectUrl;
    private String target;

    public AlertRedirectException(String message, String redirectUrl, String target, HttpStatus status) {
        super(message, status);

        target = StringUtils.hasText(target) ? target : "self";

        this.redirectUrl = redirectUrl;
        this.target = target;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getTarget() {
        return target;
    }
}
