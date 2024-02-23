package org.choongang.admin.education.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;
import org.choongang.member.entities.Member;

import java.util.UUID;

@Entity
@Data
public class EduData extends Base {
    @Id
    @GeneratedValue
    private Long num; // 자료 식별자, 자동 생성되는 고유한 번호 //pk

    @Column(length = 65)
    private String gid = UUID.randomUUID().toString();

    @Column(length = 30, nullable = false)
    private String name; // 학습자료명

    @Column(nullable = false)
    private String dataType; // 자료구분 (튜토리얼 0 / 교육영상 1)

    @Column(length = 30)
    private String contentType; // 자료유형 (동영상 / 교재 / 웹사이트)

    @Column
    private String serviceType; // 서비스구분 (무료 0 / 유료 1)

    @Column(length = 100, nullable = false)
    private String content; // 자료내용

    @Column(length = 30)
    private String fileName; // 자료file명

    @Column(length = 30)
    private String fileAddress; // 자료file주소

    @Transient
    private FileInfo thumbnail; // 썸네일 (파일명)
//    @Column
//    private String thumbnailAddress; // 썸네일 (파일명 경로)

    //////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNum")
    private Member member; //작성자 회원번호

}
