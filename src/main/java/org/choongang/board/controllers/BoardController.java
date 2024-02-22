package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

//    @GetMapping("/list/{bid}")
//    public String list(@PathVariable("bid") String bid) {
//
//        return "board/list";
//    }

    @GetMapping("/list/noticeFaq")
    public String faqList() {

        return "board/noticeFaqList";
    }

    @PostMapping("/list/noticeFaq")
    public String faqListPs() {

        return "board/noticeFaqList";
    }


    @GetMapping("list/qna")
    public String qnaList() {

        return "board/qnaList";
    }

    @PostMapping("list/qna")
    public String qnaListPs() {

        return "board/qnaList";
    }
}
