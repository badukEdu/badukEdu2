package org.choongang.admin.gamecontent.entities;

import jakarta.persistence.*;
import org.choongang.member.entities.Member;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class GameSubscriber {
    @Id
    @GeneratedValue
    private Long num;

    @Column(updatable = false)
    private String payType;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime buyDate; // 구매일 (자동생성)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNum")
    private Member member; //작성자 회원번호

}
