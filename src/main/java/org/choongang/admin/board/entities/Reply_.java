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
    private Long replyNum; // 답변 번호

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member; // 답변자 (사용자 엔티티와 관계)

    @Column(nullable = false)
    private String answer; // 답변 내용
    
}