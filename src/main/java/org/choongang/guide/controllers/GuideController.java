package org.choongang.guide.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardPosts;
import org.choongang.admin.board.entities.NoticeSearch;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.entities.requestComment;
import org.choongang.admin.board.service.BoardService;
import org.choongang.admin.gamecontent.controllers.GameContentSearch;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/guide")
@RequiredArgsConstructor
public class GuideController implements ExceptionProcessor  {

    private final GameContentInfoService gameContentInfoService;
    private final BoardService boardService;

    @GetMapping
    public String index() {
        return "redirect:/guide/intro";
    }

    @GetMapping("/intro")
    public String intro(Model model) {
        commonProcess("intro", model);

        return "guide/intro";
    }
    @GetMapping("/use")
    public String use(Model model) {
        commonProcess("use", model);

        return "guide/use";
    }

    @GetMapping("/product")
    public String guide(@ModelAttribute GameContentSearch search, Model model) {
        commonProcess("product", model);

        // 상품 소개
        ListData<GameContent> data = gameContentInfoService.getList(search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());


        return "guide/product";
    }

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

        commonProcess("detail", model);

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
        mode = StringUtils.hasText(mode) ? mode : "intro";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        addCss.add("guide/" + mode);
        String pageTitle = "이용안내";
        if (mode.equals("intro")) {
            pageTitle = "사이트 소개::" + pageTitle;
            addScript.add("guide/intro");
        }
        if (mode.equals("use")) pageTitle = "이용 가이드::" + pageTitle;
        if (mode.equals("product")) pageTitle = "상품 소개::" + pageTitle;
        if (mode.equals("notice&faq")) pageTitle = "Notice & QnA::" + pageTitle;
        if (mode.equals("qna")) pageTitle = "Q&A::" + pageTitle;
        if (mode.equals("detail")) pageTitle = "공지 상세::" + pageTitle;

        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }
}
