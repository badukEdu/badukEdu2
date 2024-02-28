package org.choongang.admin.board.entities;

import lombok.Data;

@Data
public class requestComment {
    private Long num;

    private Long commentNum;

    private String content;

    private String mode = "add";
}
