package org.choongang.admin.board.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
public class BoardFileInfo extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boardFile_seq")
    @SequenceGenerator(name = "boardFile_seq", sequenceName = "BOARDFILE_SEQ", allocationSize = 1)
    private Long num;

    @Column
    private String fileName; // 서버에 저장되는 파일명

    @Column
    private String originFileName; // 실제 파일명

    @Column
    private String filePath; // 파일 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_num")
    private Notice_ notice;

    // 기본 생성자 추가
    public BoardFileInfo() {
    }
}