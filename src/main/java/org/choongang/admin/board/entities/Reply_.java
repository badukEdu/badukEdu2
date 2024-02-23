package org.choongang.admin.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;

@Entity
@Data
public class Reply_ extends Base {

    @Id
    @GeneratedValue
    private Long num; // 답변 번호


    @ManyToOne
    @JoinColumn(name = "seq", nullable = false)
    private Member member; // 답변자 (사용자 엔티티와 관계)


    @ManyToOne
    @JoinColumn(name = "noticeSeq", nullable = false) // notice 엔티티의 seq를 사용하여 조인
    private Notice_ notice; // 문의 번호 (공지사항 엔티티와 관계)


    @Column(nullable = false)
    private String content; // 답변 내용

    // 추가적인 필드 및 매핑 설정은 필요에 따라 추가할 수 있습니다.
}