package org.choongang.teacher.homework.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;
import org.choongang.teacher.stGrooup.entities.StudyGroup;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Homework extends Base {

    @Id @GeneratedValue
    private Long num; // 숙제번호

    @Column
    private String name; // 숙제명

    @Column
    private String content; // 숙제내용

    @Column()
    private Long studyLevel; // 숙제레벨

    @Column
    private LocalDate deadLine; // 제출기한

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyGroupNum")
    private StudyGroup studyGroup; //작성자 회원번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNum")
    private Member member; //교육자 회원번호

    @OneToMany(mappedBy = "homework", fetch = FetchType.LAZY)
    private List<TrainingData> trainingDatas;
}