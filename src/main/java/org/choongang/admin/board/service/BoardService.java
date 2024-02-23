package org.choongang.admin.board.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardPosts;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.repositories.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /* 게시글(Notice, FaQ) 등록 및 수정 서비스 S */

    public void save(RequestBoardPosts form) {

        Long num = form.getNum();
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Notice_ notice;

        if (mode.equals("edit") && num != null) {
            // 수정 모드이고 게시물의 번호가 있을 때 이미 존재하는 게시글을 불러옴
            notice = boardRepository.findById(num).orElseThrow(BoardNotFoundException::new);
        } else {
            // 등록 모드일 때 새로운 게시글 생성
            notice = new Notice_();
        }

        notice.setType(form.getType());
        notice.setOnTop(form.isOnTop());
        notice.setTitle(form.getTitle());
        notice.setPostingType(form.getPostingType());
        notice.setQuestion(form.getQuestion());
        notice.setAnswer(form.getAnswer());
        notice.setContent(form.getContent());

        boardRepository.saveAndFlush(notice);
    }

    /* 게시글(Notice, FaQ) 등록 및 수정 서비스 E */



    /* 등록된 게시글 조회(정렬 기준 X, 등록 순) S */

    public List<Notice_> getList() {
        List<Notice_> noticeList = boardRepository.findAll();

        return noticeList;
    }

    /* 등록된 게시글 조회(정렬 기준 X, 등록 순) E */



    /* 노출 여부를 기준으로 게시물 조회 S */

    public List<Notice_> getListOrderByOnTop() {
        return boardRepository.findByOrderByOnTopDesc();
    }

    /* 등록된 게시글 조회(정렬 기준 X, 등록 순) S */

    /* 게시글 번호로 상세 페이지 조회 S */
    public Optional<Notice_> findByNum(Long num) {
        return boardRepository.findById(num);
    }

    /* 게시글 번호로 상세 페이지 조회 E */



    /* 게시글 삭제 S */

    public void deleteById(Long num) {

        boardRepository.deleteById(num);
    }

    /* 게시글 삭제 E */



}
