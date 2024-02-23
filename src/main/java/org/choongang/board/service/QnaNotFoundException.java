package org.choongang.board.service;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class QnaNotFoundException extends AlertBackException {

    public QnaNotFoundException() {
        super("게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}

