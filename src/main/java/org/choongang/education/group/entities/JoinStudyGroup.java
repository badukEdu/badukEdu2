package org.choongang.education.group.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.entities.StudyGroup;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class JoinStudyGroup {

    @Id
    @GeneratedValue
    private Long num;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime SDate; // 신청일 (자동생성)
    @Column
    private boolean accept = false; // 가입승인여부

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime JoinDate; // 가입 승인일 (자동생성)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyGroupNum")
    private StudyGroup studyGroup; // 스터디그룹

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNum")
    private Member member; //신청한 회원 정보

}
