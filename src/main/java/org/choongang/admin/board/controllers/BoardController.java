package org.choongang.admin.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.entities.NoticeSearch;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.service.BoardService;
import org.choongang.commons.ListData;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController implements ExceptionProcessor  {

    private final BoardService boardService;

    /* 공지사항 및 FAQ 게시글 등록 및 수정 S */

    @GetMapping("noticeFaqAdd")
    public String board_posts(Model model) {

        commonProcess("posts", model);
        model.addAttribute("RequestBoardPosts", new RequestBoardPosts());

        return "admin/board/noticeFaqAdd";
    }



    @PostMapping("noticeFaqAdd")
    public String board_postsPs(RequestBoardPosts form, Model model) {

        boardService.save(form);

        return "redirect:/admin/board/noticeFaqList";
    }

    /* 공지사항 및 FAQ 게시글 등록 및 수정 E */



    /* 공지사항 및 FAQ 게시글 목록 조회 S */


    @GetMapping("/noticeFaqList")
    public String adminnoticeFaqList(@ModelAttribute NoticeSearch search, Model model) {


        ListData<Notice_> noticeList = boardService.getListOrderByOnTop(search);

        List<Notice_> immediatelyList = noticeList.getItems().stream()
                .filter(notice -> notice.getPostingType().equals("immediately"))
                .collect(Collectors.toList());

        // 게시 대기 리스트 가져오기
        List<Notice_> waitingList = noticeList.getItems().stream()
                .filter(notice -> notice.getPostingType().equals("expectedPostingDate"))
                .collect(Collectors.toList());

        model.addAttribute("waitingList", waitingList);
        model.addAttribute("noticeList", noticeList.getItems());
        model.addAttribute("pagination", noticeList.getPagination());


        return "admin/board/noticeFaqList";
    }

    /* 공지사항 및 FAQ 게시글 목록 조회 E */





    /* 공지 사항 및 FAQ 게시글 상세 조회 S */


    @GetMapping("/detail/{num}")
    public String detail(@PathVariable("num") Long num, Model model){

        // 경로 변수 num이 null이거나 음수인 경우에는 admin/board/noticeFaqList로 리다이렉션
        if (num <= 0) {
            return "redirect:/admin/board/noticeFaqList";
        }

        // 게시글 번호를 사용하여 해당 게시글 정보를 가져온다.
        Optional<Notice_> noticeDetail = boardService.findByNum(num);

        // 게시글이 존재하는 경우에는 모델에 추가하고 admin/board/noitceDetail 페이지를 반환
        if (noticeDetail.isPresent()) {
            // 조회수 증가
            boardService.increaseVisitCount(num);
            model.addAttribute("noticeDetail", noticeDetail.get());
            model.addAttribute("requestBoardPosts", new RequestBoardPosts());

            return "admin/board/noticeFaqDetail";
        }

        // 해당 게시글을 찾을 수 없는 경우에는 admin/board/noticeFaqList로 리다이렉션
        return "redirect:/admin/board/noticeFaqList";
    }


    /* 공지 사항 및 FAQ 게시글 상세 조회 E */


    /* 공지사항 및 FAQ 게시글 수정 S */

    @GetMapping("/edit/{num}")
    public String editForm(@PathVariable("num") Long num, Model model) {

        // 게시글 번호를 사용하여 해당 게시글 정보를 가져온다.
        Optional<Notice_> noticeDetail = boardService.findByNum(num);

        // 게시글이 존재하는 경우에는 모델에 추가하고 admin/board/noticeEdit 페이지를 반환
        if (noticeDetail.isPresent()) {
            model.addAttribute("requestBoardPosts", noticeDetail.get());
            return "admin/board/noticeFaqEdit";
        }

        // 해당 게시글을 찾을 수 없는 경우에는 admin/board/noticeFaqList로 리다이렉션
        return "redirect:/admin/board/noticeFaqList";
    }

    @PostMapping("/edit")
    public String editBoard(RequestBoardPosts form, Model model) {
        // 기존 게시물 정보 가져오기
        Optional<Notice_> existingNotice = boardService.findByNum(form.getNum());

        // 기존 게시물이 존재하는 경우에만 수정
        if (existingNotice.isPresent()) {
            Notice_ notice = existingNotice.get();

            // 기존 게시물 내용 수정
            notice.setTitle(form.getTitle());
            notice.setPostingType(form.getPostingType());
            notice.setQuestion(form.getQuestion());
            notice.setAnswer(form.getAnswer());
            notice.setContent(form.getContent());

            // 수정된 게시물 저장
            boardService.save(form);
        }

        return "redirect:/admin/board/noticeFaqList";
    }

    /* 공지사항 및 FAQ 게시글 수정 E */


    /* 공지사항 및 FAQ 게시글 삭제 S */

    @GetMapping("/delete/{num}")
    public String deleteBoard(@PathVariable("num") Long num) {
        boardService.deleteById(num);
        return "redirect:/admin/board/noticeFaqList";
    }
    /* 공지사항 및 FAQ 게시글 삭제 E */


    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "posts";
        String pageTitle = "운영마당";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("posts")) {
            pageTitle = "공지사항 " + (mode == "edit" ? "수정" : "등록") + " ::" + pageTitle;
            addScript.add("admin/Board/form");
            addScript.add("fileManager");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", "board_" + mode);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
    }
}
