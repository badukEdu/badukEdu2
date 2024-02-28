package org.choongang.admin.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.entities.NoticeComment;
import org.choongang.admin.board.entities.requestComment;
import org.choongang.admin.board.service.CommentService;
import org.choongang.commons.ListData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /* 댓글 등록 및 수정 */

    @PostMapping("/create")
    public String createComment(@ModelAttribute requestComment comment, Model model) {

        commentService.save(comment);

        model.addAttribute("script", "parent.location.reload();");

        return "common/_execute_script";
    }

    @GetMapping("/delete/{commentNum}")
    public String deleteComment(@PathVariable("commentNum") Long commentNum, Model model) {

        commentService.delete(commentNum);

        model.addAttribute("script", "parent.location.reload();");

        return "common/_execute_script";

    }
}
