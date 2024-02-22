package org.choongang.teacher.homework.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
public class TrainingData {
  @Id
  @GeneratedValue
  private Long num; // 자료 식별자, 자동 생성되는 고유한 번호 //pk
  @Column(updatable = false)
  private LocalDateTime SDate; // 숙제 생성일 (선생님이 숙제를 내 준 날)
  @Column
  private String homeworkAnswer; // 숙제 정답 (학생이 제출한 정답)
  @Column
  private String question; // 질의내용
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime sendDate; // 제출일 (제출시 자동 저장)
  @Column
  private String questionAnswer; // 질의 답변내용
  @Column
  private Long score; // 체점 점수

  @Column(updatable = false)
  private LocalDateTime aDate; // 숙제 평가일

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberNum")
  private Member member; //작성자 회원번호

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "homeworkNum")
  private Homework homework; // 숙제
}