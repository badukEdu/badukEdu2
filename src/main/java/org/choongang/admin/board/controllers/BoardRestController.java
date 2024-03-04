package org.choongang.admin.board.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.choongang.admin.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/board")
@RequiredArgsConstructor
@Slf4j
@RestController
public class BoardRestController {

    private final BoardService boardService;

//    @DeleteMapping("notice")
//    public int noticesDelete(@RequestBody String[] nums) {
//        return boardService.deleteNotices(nums);
//    }
}
