package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardPosts;
import org.choongang.admin.board.entities.NoticeSearch;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.entities.requestComment;
import org.choongang.commons.ListData;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.choongang.admin.board.service.BoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/guide")
@RequiredArgsConstructor
public class BoardController implements ExceptionProcessor  {

    private final BoardService boardService;

    @GetMapping("/list/noticeFaq")
    public String adminnoticeFaqList(@ModelAttribute NoticeSearch search, Model model) {
        commonProcess("notice&faq", model);

        ListData<Notice_> noticeList = boardService.getListOrderByOnTop(search);
        model.addAttribute("noticeList", noticeList.getItems());
        model.addAttribute("pagination", noticeList.getPagination());


        return "board/noticeFaqList2";
    }

    @GetMapping("/detail/{num}")
    public String detail(@PathVariable("num") Long num, @ModelAttribute requestComment form, Model model){

        // 경로 변수 num이 null이거나 음수인 경우에는 board/list/noticeFaq로 리다이렉션
        if (num <= 0) {
            return "redirect:/guide/list/noticeFaq";
        }

        // 게시글 번호를 사용하여 해당 게시글 정보를 가져온다.
        Optional<Notice_> noticeDetail = boardService.findByNum(num);

        // 게시글이 존재하는 경우에는 모델에 추가하고 board/noitceDetail2 페이지를 반환
        if (noticeDetail.isPresent()) {

            boardService.increaseVisitCount(num);
            model.addAttribute("noticeDetail", noticeDetail.get());
            model.addAttribute("requestBoardPosts", new RequestBoardPosts());

            return "board/noticeFaqDetail2";
        }

        // 해당 게시글을 찾을 수 없는 경우에는 board/list/noticeFaq로 리다이렉션
        return "redirect:/guide/list/noticeFaq";

    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list/noticeFaq";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        String pageTitle = "이용안내";
        if (mode.equals("notice&faq")) {
            pageTitle = mode + " ::" + pageTitle;
            addCss.add("guide/" + mode);
            addScript.add("fileManager");
        }

        model.addAttribute("addCss", addCss);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }

}
