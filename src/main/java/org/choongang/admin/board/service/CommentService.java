package org.choongang.admin.board.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.entities.NoticeComment;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.entities.requestComment;
import org.choongang.admin.board.repositories.BoardRepository;
import org.choongang.admin.board.repositories.NoticeCommentRepository;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final NoticeCommentRepository noticeCommentRepository;
    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;

    /* 댓글 작성 및 수정 메서드 */

    public void save(requestComment form) {

        NoticeComment noticeComment = new NoticeComment();

        Long num = form.getNum();
        String mode = form.getMode();

        if (mode.equals("edit")) {
            noticeCommentRepository.findById(form.getCommentNum());
        }

        Notice_ notice = boardRepository.findById(num).orElseThrow();

        noticeComment.setContent(form.getContent()); // 내용
        noticeComment.setMember(memberUtil.getMember()); // 작성자
        noticeComment.setNotice(notice);

        noticeCommentRepository.saveAndFlush(noticeComment);
    }


    // 댓글 삭제 메서드
    public void delete(Long commentNum) {

        noticeCommentRepository.deleteById(commentNum);
    }

}

