package org.choongang.admin.board.entities;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class NoticeSearch {
    private int page = 1;
    private int limit = 10;
    private String sopt = "ALL"; // 검색옵션
    private String skey = ""; // 검색 키워드
    private String onTop;
}
