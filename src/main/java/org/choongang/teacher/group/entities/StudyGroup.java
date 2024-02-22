package org.choongang.teacher.group.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.education.group.entities.JoinStudyGroup;
import org.choongang.member.entities.Member;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class StudyGroup {

    @Id
    @GeneratedValue
    private Long num;    //기본키

    @Column
    private Long month;

    @Column(length = 80 , unique = true , nullable = false)
    private String name;     //스터디그룹명

    @Column
    private LocalDate startDate; //시작일

    @Column
    private LocalDate endDate; //종료일

    @Column(length = 80 )
    private Long maxSubscriber;    //최대인원

    @Column
    private Long maxLevel;   //달성 레벨

    @Column(length = 80 )
    private String text;     //비고

    @Column
    private String gameTitle;   //게임컨텐츠명

    @Column
    private String teacherName;   //게임컨텐츠명

    ////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNum")
    private Member member; //작성자 회원번호

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameContentNum")
    private GameContent gameContent; //게임 번호

    @OneToMany(mappedBy = "studyGroup", fetch = FetchType.LAZY)
    private List<Homework> homeworks;

    @OneToMany(mappedBy = "studyGroup", fetch = FetchType.LAZY)
    private List<JoinStudyGroup> joinStudyGroups;

}
