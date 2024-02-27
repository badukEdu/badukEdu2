package org.choongang.admin.gamecontent.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.BaseMember;
import org.choongang.file.entities.FileInfo;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.entities.StudyGroup;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class GameContent extends BaseMember {
    @Id
    @GeneratedValue
    private Long num; // 게임 식별자, 자동 생성되는 고유한 번호 // pk

    @Column(length = 65)
    private String gid = UUID.randomUUID().toString();

    @Column(length = 65, nullable = false)
    private String gameTitle; // 게임콘텐츠명

    @Column
    private Long totalGameLevels; // 총게임레벨

    @Column
    private LocalDate startDate; //구독 시작일 (구독가능기간)

    @Column
    private LocalDate endDate; //구독 종료일 (구독가능기간)

    @Column
    private int subscriptionMonths; // 구독개월

    @Column
    private Long maxSubscriber; //최대인원(구독가능인원)

    @Column
    private Long originalPrice; // 정가

    @Column
    private int salePrice; // 판매가

    @Column(length = 100, nullable = false)
    private String packageContents; // 패키지내용

    @Column
    private boolean use;  // 삭제여부 (사용중 1 / 삭제 0)

    @Column
    private float discountRate; // 할인율

    @Transient
    private FileInfo thumbnail;

    @Transient
    private int stgroupCount = 0;

    @Transient
    private Long stgroupCount2 = 0L; //사용중-표찬
///////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNum")
    private Member member; //작성자 회원번호

    @OneToMany(mappedBy = "gameContent", fetch = FetchType.LAZY)
    private List<StudyGroup> studyGroups;

//private List<StudyGroup> studyGroups;


}
