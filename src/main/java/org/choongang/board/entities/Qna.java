package org.choongang.board.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qna_seq")
    @SequenceGenerator(name = "qna_seq", sequenceName = "QNA_SEQ", allocationSize = 1)
    private Long num;

    private String type;

    private String title;

    private String answerAlert;

    private String content;

    @Column
    private String fileName; // 파일명 (파일명)

    @Column
    private String fileAddress; // 파일경로 (파일 경로)

}
