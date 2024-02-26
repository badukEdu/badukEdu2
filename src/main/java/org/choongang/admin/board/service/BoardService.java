package org.choongang.admin.board.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.choongang.admin.board.controllers.RequestBoardPosts;
import org.choongang.admin.board.entities.NoticeSearch;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.entities.QNotice_;
import org.choongang.admin.board.repositories.BoardRepository;
import org.choongang.admin.education.entities.EduData;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final HttpServletRequest request;
    private final FileInfoService fileInfoService;
    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;

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
        notice.setMember(memberUtil.getMember());

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

    public ListData<Notice_> getListOrderByOnTop(NoticeSearch search) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";

        QNotice_ notice = QNotice_.notice_;

        /* 검색 조건 처리 S */

        // 삭제된 데이터 제외
        BooleanBuilder andBuilder = new BooleanBuilder();

        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            BooleanExpression titleConds = notice.title.contains(skey);
            BooleanExpression contentConds = notice.content.contains(skey);
            BooleanExpression typeConds = notice.type.contains(skey);

            if (sopt.equals("ALL")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(titleConds).or(contentConds).or(typeConds));

            } else if (sopt.equals("title")) {
                andBuilder.and(titleConds);
            } else if (sopt.equals("content")) {
                andBuilder.and(contentConds);
            } else if (sopt.equals("type")) {
                andBuilder.and(typeConds);
            }
        }

        Pageable pageable;
        if (StringUtils.hasText(search.getOnTop()) && search.getOnTop().equals("O")) {
            // onTop이 'O'인 경우, 가장 최근 게시물을 제일 위에 출력하도록 설정
            pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        } else {
            // onTop이 'O'가 아닌 경우, 일반적인 등록일 기준으로 정렬
            pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "onTop", "createdAt"));
        }
        Page<Notice_> data = boardRepository.findAll(andBuilder, pageable);
        int total = (int) boardRepository.count(andBuilder);

        data.getContent().forEach(this::addInfo);

        // 결과 반환

        Pagination pagination = new Pagination(page, total, 10, limit, request);

        return new ListData<>(data.getContent(), pagination);
    }

    /* 노출 여부를 기준으로 게시물 조회 E */


    /* 게시 예정인 게시물을 가져오는 메소드 S */


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

    public Notice_ getById(Long num) {

        Notice_ data = boardRepository.getById(num);
        addInfo(data);

        return data;
    }

    public RequestBoardPosts getForm(Long num) {
        Notice_ data = getById(num);
        RequestBoardPosts form = new ModelMapper().map(data, RequestBoardPosts.class);
        form.setNum(data.getNum());
        return form;
    }



    private void addInfo(Notice_ data) {
        List<FileInfo> items = fileInfoService.getListDone(data.getGid());
        if(items != null && !items.isEmpty()) data.setThumbnail(items.get(0));

    }

}
