package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardPosts;
import org.choongang.admin.board.entities.Notice_;
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
public class BoardController {

    private final BoardService boardService;

//    @GetMapping("/list/{bid}")
//    public String list(@PathVariable("bid") String bid) {
//
//        return "board/list";
//    }

    @GetMapping("/list/noticeFaq")
    public String adminnoticeFaqList(@ModelAttribute Notice_ form, Model model) {

        commonProcess("notice&faq", model);

        List<Notice_> noticeList = boardService.getListOrderByOnTop();
        model.addAttribute("noticeList", noticeList);

        return "board/noticeFaqList";
    }


    @GetMapping("list/qna")
    public String qnaList(Model model) {

        model.addAttribute("RequestQnaAdd", new RequestQnaAdd());

        return "board/qnaList";
    }

    @GetMapping("/detail/{num}")
    public String detail(@PathVariable Long num, Model model){

        // 경로 변수 num이 null이거나 음수인 경우에는 board/list/noticeQna로 리다이렉션
        if (num <= 0) {
            return "redirect:/guide/list/noticeQna";
        }

        // 게시글 번호를 사용하여 해당 게시글 정보를 가져온다.
        Optional<Notice_> noticeDetail = boardService.findByNum(num);

        // 게시글이 존재하는 경우에는 모델에 추가하고 admin/board/noitceDetail 페이지를 반환
        if (noticeDetail.isPresent()) {
            model.addAttribute("noticeDetail", noticeDetail.get());
            model.addAttribute("requestBoardPosts", new RequestBoardPosts());

            return "admin/board/postsDetail";
        }

        // 해당 게시글을 찾을 수 없는 경우에는 board/list/noticeQna로 리다이렉션
        return "redirect:/guide/list/noticeQna";

    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list/noticeFaq";
        List<String> addCss = new ArrayList<>();
        addCss.add("guide/" + mode);
        String pageTitle = "이용안내";
        if (mode.equals("list/noticeFaq")) pageTitle = "Notice & FaQ::" + pageTitle;

        model.addAttribute("addCss", addCss);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }

}
