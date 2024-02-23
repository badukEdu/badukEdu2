package org.choongang.admin.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;

@Entity
@Data
public class NoticeComent extends Base {

    @Id
    @GeneratedValue
    private Long num; // 댓글 번호

    @ManyToOne
    @JoinColumn(name = "notice_Num") // 공지사항 번호 (FK)
    private Notice_ notice; // 댓글이 속한 공지사항

    @Lob
    private String content; // 댓글 내용

    ///////////////////////////

    @ManyToOne
    @JoinColumn(name = "memberNum") // 작성자 (FK)
    private Member member;

}