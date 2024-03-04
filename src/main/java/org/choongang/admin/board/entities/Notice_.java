package org.choongang.admin.board.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;
import org.choongang.member.entities.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data // getter, setter, NoArgsConstructor 등 필요한 것만 구성 가능
public class Notice_ extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq")
    @SequenceGenerator(name = "notice_seq", sequenceName = "NOTICE_SEQ", allocationSize = 1)
    private Long num; // 공지사항(게시물) 번호

    @Column(length = 65)
    private String gid = UUID.randomUUID().toString();

    @Column
    @NotNull
    private String type; // 공지사항 분류(공지사항, FAQ , QnA-> ENUM 클래스 사용?)

    @Column(nullable = true)
    private boolean onTop; // 중요글 상단 노출(공지사항일때만 적용)

    @Column
    private String title; // 제목

    @Lob // 가변 길이의 문자열을 저장, 길이 제한 X
    private String content; // 내용

    @Column
    private Long visit; //조회수

    @Column(nullable = false)
    private String postingType; // 게시 타입(즉시, 예정)

    @Column
    private LocalDate scheduledDate; // 예약 게시 일자

    @Column
    private String question; // 질의(FaQ일 경우에만 사용)

    @Column
    private String answer; // 답변(FaQ일 경우에만 사용)

////////////////////////////

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BoardFileInfo> file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member; // 작성자 아이디

    @ToString.Exclude
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<NoticeComment> noticeComments;

}