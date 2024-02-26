package org.choongang.admin.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.entities.NoticeComment;
import org.choongang.admin.board.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /* 댓글 등록 및 수정 */
    @PostMapping("/create")
    public String createComments() {

        return "";
    }

    @GetMapping("/list")
    public String commentsList() {

        return "";
    }
}
