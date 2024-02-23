package org.choongang.board.service;

import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.RequestQnaAdd;
import org.choongang.board.entities.Qna;
import org.choongang.board.repositories.QnaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {

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

    public List<Qna> getList() {
        List<Qna> qnaList = qnaRepository.findAll();
        return qnaList;
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
}
