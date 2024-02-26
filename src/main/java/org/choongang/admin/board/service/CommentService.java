package org.choongang.admin.board.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.entities.NoticeComment;
import org.choongang.admin.board.repositories.NoticeCommentRepository;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final NoticeCommentRepository noticeCommentRepository;
    private final MemberUtil memberUtil;

    // 댓글 작성 및 수정 메서드
//    public NoticeComment createComment() {
//
//        return noticeCommentRepository.save();
//    }
//
//
//    // 댓글 리스트 조회 메서드
//    public List<NoticeComment> getAllComments() {
//
//        return noticeCommentRepository.findAll();
//    }
//
//    // 댓글 삭제 메서드
//    public void deleteComment() {
//
//        noticeCommentRepository.deleteCommentById();
//    }
//


}

