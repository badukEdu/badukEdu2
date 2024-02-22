package org.choongang.admin.board.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController {



    @GetMapping("posts")
    public String board_posts(Model model) {
        model.addAttribute("RequestBoardPosts", new RequestBoardPosts());

        return "admin/board/posts";
    }

    @PostMapping("posts")
    public String board_postsPs(RequestBoardPosts form, Model model) {

        return "admin/board/posts";
    }

}
