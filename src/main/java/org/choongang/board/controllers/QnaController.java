package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.Qna;
import org.choongang.board.entities.QnaSearch;
import org.choongang.board.service.QnaService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/guide")
@RequiredArgsConstructor
public class QnaController implements ExceptionProcessor  {

    private final QnaService qnaService;

    /* QnA 게시글 목록 조회 S */
    @GetMapping("/qnaList")
    public String list(@ModelAttribute QnaSearch search, Model model) {
        commonProcess("list", model);

        ListData<Qna> qnaList = qnaService.getList(search);

        model.addAttribute("qnaList", qnaList.getItems());
        model.addAttribute("pagination", qnaList.getPagination());

        return "board/qnaList";
    }

    @PostMapping("/qnaList")
    public String qnaList(Model model) {
        commonProcess("list", model);

        return "board/qnaList";
    }

    /* QnA 게시글 목록 조회 E */

    /* QnA 게시글 등록(이용자, 관리자 권한) S */
    @GetMapping("/add")
    public String qnaAdd(@ModelAttribute  RequestQnaAdd form, Model model) {
        commonProcess("add", model);


        return "board/qnaAdd";
    }

    @PostMapping("/add")
    public String qnaList(RequestQnaAdd form, Model model) {
        commonProcess("add", model);

        qnaService.save(form);

        return "redirect:/guide/qnaList";
    }
    /* QnA 게시글 등록(이용자, 관리자 권한) E */




    /* QnA 게시글 상세 조회 S */

    @GetMapping("/qnaDetail/{num}")
    public String detail(@PathVariable("num") Long num, Model model) {
        commonProcess("detail", model);

        // 경로 변수 num이 null이거나 음수인 경우에는 /guide/qnaList로 리다이렉션
        if (num <= 0) {
            return "redirect:/guide/qnaList";
        }

        // 게시글 번호를 사용하여 해당 게시글 정보를 가져온다.
        Optional<Qna> qnaDetail = qnaService.qnaFindByNum(num);

        // 게시글이 존재하는 경우에는 모델에 추가하고 /board/qnaDetail 페이지를 반환
        if (qnaDetail.isPresent()) {
            model.addAttribute("qnaDetail", qnaDetail.get());
            model.addAttribute("requestQnaAdd", new RequestQnaAdd());
            return "board/qnaDetail";
        }

        // 해당 게시글을 찾을 수 없는 경우에는 /guide/qnaList로 리다이렉션
        return "redirect:/guide/qnaList";
    }

    /* QnA 게시글 상세 조회 E */

    /* QnA 게시글 수정 S */

    @GetMapping("/qnaEdit/{num}")
    public String editForm(@PathVariable("num") Long num, Model model) {
        commonProcess("edit", model);
        // 게시글 번호를 사용하여 해당 게시글 정보를 가져온다.
        Optional<Qna> qnaDetail = qnaService.qnaFindByNum(num);

        // 게시글이 존재하는 경우에는 모델에 추가하고 admin/board/qnaEdit 페이지를 반환
        if (qnaDetail.isPresent()) {
            model.addAttribute("requestQnaAdd", qnaDetail.get());

            return "board/qnaEdit";
        }

        // 해당 게시글을 찾을 수 없는 경우에는 guide/qnaList로 리다이렉션
        return "redirect:/guide/qnaList";
    }

    @PostMapping("/qnaEdit")
    public String editqna(RequestQnaAdd form, Model model) {
        commonProcess("edit", model);
        // 기존 게시물 정보 가져오기
        Optional<Qna> existingQna = qnaService.qnaFindByNum(form.getNum());

        // 기존 게시물이 존재하는 경우에만 수정
        if (existingQna.isPresent()) {
            Qna qna = existingQna.get();

            // 기존 게시물 내용 수정
            qna.setType(form.getType());
            qna.setTitle(form.getTitle());
            qna.setAnswerAlert(form.getAnswerAlert());
            qna.setContent(form.getContent());
            qna.setFileName(form.getFileName());
            qna.setFileAddress(form.getFileAddress());

            // 수정된 게시물 저장
            qnaService.save(form);

        }

        return "redirect:/guide/qnaList";
    }

    /* QnA 게시글 수정 E */

    /* QnA 게시글 삭제 S */

    @GetMapping("/qnaDelete/{num}")
    public String deleteQna(@PathVariable("num") Long num, Model model) {
        commonProcess("delete", model);
        qnaService.deleteById(num);

        return "redirect:/guide/qnaList";
    }

    /* QnA 게시글 삭제 E */

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "add";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        addCss.add("guide/" + mode);
        String pageTitle = "이용안내";
        if (mode.equals("list")) pageTitle = "QnA::" + pageTitle;
        else if (mode.equals("add") || mode.equals("edit")) {
            addScript.add("fileManager");
        }

        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }


}
