package org.choongang.board.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardPosts;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.board.controllers.RequestQnaAdd;
import org.choongang.board.entities.QQna;
import org.choongang.board.entities.Qna;
import org.choongang.board.entities.QnaSearch;
import org.choongang.board.repositories.QnaRepository;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final HttpServletRequest request;
    private final FileInfoService fileInfoService;
    private final QnaRepository qnaRepository;


    /* QnA 등록, 수정 서비스 S */

    public void save(RequestQnaAdd form) {
        Long num = form.getNum();
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Qna qna;

        if (mode.equals("edit") && num != null) {
            qna = qnaRepository.findById(num).orElseThrow(QnaNotFoundException::new);
        } else {
            qna = new Qna();
        }

        qna.setType(form.getType());
        qna.setTitle(form.getTitle());
        qna.setAnswerAlert(form.getAnswerAlert());
        qna.setContent(form.getContent());
        qna.setFileName(form.getFileName());
        qna.setFileAddress(form.getFileAddress());

        qnaRepository.saveAndFlush(qna);
    }

    /* QnA 등록, 수정 서비스 E */


    /* 등록된 QnA 조회(등록 순) S */

    public ListData<Qna> getList(QnaSearch search) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";

        QQna qna = QQna.qna;

        /* 검색 조건 처리 S */

        BooleanBuilder andBuilder = new BooleanBuilder();
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            BooleanExpression titleConds = qna.title.contains(skey);
            BooleanExpression contentConds = qna.content.contains(skey);
            if (sopt.equals("ALL")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(titleConds).or(contentConds));

            } else if (sopt.equals("title")) {
                andBuilder.and(titleConds);
            } else if (sopt.equals("content")) {
                andBuilder.and(contentConds);
            }
        }

        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Qna> data = qnaRepository.findAll(andBuilder, pageable);
        int total = (int)qnaRepository.count(andBuilder);

        data.getContent().forEach(this::addInfo);

        Pagination pagination = new Pagination(page, total, 10, limit ,request);

        return new ListData<>(data.getContent(), pagination);
    }


    /* 등록된 QnA 조회(등록 순) E */


    /* 게시글 번호로 상세 페이지 조회 S */

    public Optional<Qna> qnaFindByNum(Long num) {
        return qnaRepository.findById(num);
    }

    /* 게시글 번호로 상세 페이지 조회 E */


    /* QnA 게시글 삭제 S */

    public void deleteById(Long num) {
        qnaRepository.deleteById(num);
    }

    /* QnA 게시글 삭제 E */

    public Qna getById(Long num) {

        Qna data = qnaRepository.getById(num);
        addInfo(data);

        return data;
    }


    public RequestQnaAdd getForm(Long num) {
        Qna data = getById(num);
        RequestQnaAdd form = new ModelMapper().map(data, RequestQnaAdd.class);
        form.setNum(data.getNum());
        return form;
    }


    private void addInfo(Qna data) {
        List<FileInfo> items = fileInfoService.getListDone(data.getGid());
        if(items != null && !items.isEmpty()) data.setThumbnail(items.get(0));

    }
}
