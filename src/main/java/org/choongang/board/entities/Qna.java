package org.choongang.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;
import org.choongang.member.entities.Member;

import java.util.UUID;

@Entity
@Data
public class Qna extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qna_seq")
    @SequenceGenerator(name = "qna_seq", sequenceName = "QNA_SEQ", allocationSize = 1)
    private Long num;

    @Column(length = 65)
    private String gid = UUID.randomUUID().toString();

    private String type;

    private String title;

    private String answerAlert;

    private String content;

    @Column
    private String fileName; // 파일명 (파일명)

    @Column
    private String fileAddress; // 파일경로 (파일 경로)

    @Column
    private String answer;

    @Transient
    private FileInfo thumbnail; // 썸네일 (파일명)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member; // 작성자 아이디

}
