package org.choongang.admin.gamecontent.service;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class GameContentNotFoundException extends AlertBackException {

    public GameContentNotFoundException() {
        super("게임 컨텐츠를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
