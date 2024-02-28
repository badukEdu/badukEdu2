package org.choongang.admin.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;

@Entity
@Data
public class NoticeComment extends Base {

    @Id
    @GeneratedValue
    private Long commentNum; // 댓글 번호

    @Column(nullable = false)
    private String content; // 댓글 내용

    @Column(nullable = false)
    private boolean deleted; // 삭제 여부(true / false)로 구분

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "num") // 공지사항 번호 (FK)
    private Notice_ notice; // 댓글이 속한 공지사항

    @ManyToOne
    @JoinColumn(name = "userId") // 작성자 (FK)
    private Member member;



}