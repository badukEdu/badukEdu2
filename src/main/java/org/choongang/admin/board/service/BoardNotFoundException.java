package org.choongang.admin.board.service;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertBackException {

    public BoardNotFoundException() {
        super("게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}