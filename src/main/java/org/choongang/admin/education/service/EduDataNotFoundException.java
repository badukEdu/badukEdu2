package org.choongang.admin.education.service;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class EduDataNotFoundException extends AlertBackException  {

    public EduDataNotFoundException() {
        super("학습 자료를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
