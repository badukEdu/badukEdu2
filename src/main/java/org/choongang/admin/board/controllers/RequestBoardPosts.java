package org.choongang.admin.board.controllers;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RequestBoardPosts {

    private Long num;

    private String gid = UUID.randomUUID().toString();

    private String type; // 공지 분류(notice 또는 faq)
    
    private String mode = "edit"; // 등록 및 수정 분리

    private boolean onTop;

    private String title;

    private String postingType; // 게시 타입

    private String question;

    private String answer;

    private String content;

   // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
   // private LocalDateTime expectedPostingDate = LocalDateTime.now().plusDays(1);

    public boolean isOnTop()
    {
        return onTop;
    }


}
