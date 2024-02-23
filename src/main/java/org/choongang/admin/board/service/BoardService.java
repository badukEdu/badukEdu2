package org.choongang.admin.board.service;

//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardPosts;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.repositories.BoardRepository;
//import org.choongang.admin.education.entities.EduData;
//import org.choongang.commons.ListData;
//import org.choongang.commons.Pagination;
//import org.choongang.commons.Utils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

//import static org.springframework.data.domain.Sort.Order.desc;

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

//    public ListData<BoardData> getList(BoardDataSearch search) {
//
//        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
//        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);
//        String sopt = search.getSopt();
//        String skey = search.getSkey();
//        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";
//
//        QBoardData boardData = QBoardData.boardData;
//
//        /* 검색 조건 처리 S */
//        BooleanBuilder andBuilder = new BooleanBuilder();
//        if (StringUtils.hasText(skey)) {
//            skey = skey.trim();
//            BooleanExpression nameConds = boardData.name.contains(skey);
//            BooleanExpression contentConds = boardData.content.contains(skey);
//            if (sopt.equals("ALL")) {
//                BooleanBuilder orBuilder = new BooleanBuilder();
//                andBuilder.and(orBuilder.or(nameConds).or(contentConds));
//
//            } else if (sopt.equals("name")) {
//                andBuilder.and(nameConds);
//            } else if (sopt.equals("content")) {
//                andBuilder.and(contentConds);
//            }
//        }
//
//        /* 검색 조건 처리 E */
//
//        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
//
//        Page<EduData> data = BoardDataRepository.findAll(andBuilder, pageable);
//        int total = (int)BoardDataRepository.count(andBuilder);
//
//        data.getContent().forEach(this::addInfo);
//
//        Pagination pagination = new Pagination(page, total, 10, limit ,request);
//
//        return new ListData<>(data.getContent(), pagination);
//    }


    /* 게시글 삭제 S */

    public void deleteById(Long num) {

        boardRepository.deleteById(num);
    }

    /* 게시글 삭제 E */



}
