package org.choongang.admin.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class RequestBoardPosts {

    private Long num;

    private String gid = UUID.randomUUID().toString();

    private String type; // 공지 분류(notice 또는 faq)
    
    private String mode = "edit"; // 등록 및 수정 분리

    private boolean onTop;

    private String title;

    @NotBlank
    private String postingType; // 게시 타입

    private LocalDate scheduledDate; // 예약 게시 일자

    private String question;

    private String answer;

    private String content;

    private List<MultipartFile> uploadFile;

   // @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
   // private LocalDateTime expectedPostingDate = LocalDateTime.now().plusDays(1);

//    public boolean isOnTop()
//    {
//        return onTop;
//    }


}
