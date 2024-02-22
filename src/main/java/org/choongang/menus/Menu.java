package org.choongang.menus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final static Map<String, List<MenuDetail>> menus;

    static {

        menus = new HashMap<>();
        //이용안내 / 권한 - ALL
        menus.put("guide", Arrays.asList(
                new MenuDetail("intro", "사이트 소개", "/guide/intro"),
                new MenuDetail("use", "이용 가이드", "/guide/use"),
                new MenuDetail("product", "상품 소개", "/guide/product"),
                new MenuDetail("faq", "FaQ", "/board/list/faq") ,
                new MenuDetail("qna", "Q&A", "/board/list/qna")
        ));


        // 구독서비스 / 권한 - TEACHER , USER
        menus.put("subscription", Arrays.asList(
                new MenuDetail("apply", "게임콘텐츠 검색 및 구독신청", "/subscription/subscribe"),
                new MenuDetail("list", "내 게임콘텐츠 조회", "/subscription")
        ));

        // 학습서비스 / 권한 - STUDENT , ADMIN
        menus.put("education", Arrays.asList(
                new MenuDetail("join", "학습그룹 가입 신청", "/education/join")
        ));

        // 교육자마당 / 권한 - TEACHER , ADMIN
        menus.put("teacher", Arrays.asList(
                new MenuDetail("add", "학습 그룹 등록", "/teacher/group/add"),//
                new MenuDetail("list", "학습 그룹 조회", "/teacher/group"),//
                new MenuDetail("accept", "학습 그룹 가입 승인", "/teacher/group/accept"),
                new MenuDetail("homework_add", "숙제 생성", "/teacher/homework/add"),
                new MenuDetail("distribute", "숙제 배포", "/teacher/homework/distribute"),
                new MenuDetail("homework_list", "숙제 학습 진도 조회", "/teacher/homework")
        ));

        // 운영마당 / 권한 - ADMIN
        menus.put("admin", Arrays.asList(
                new MenuDetail("gamecontent_add", "게임콘텐츠 등록", "/admin/gamecontent/add"),
                new MenuDetail("gamecontent_list", "게임콘텐츠 조회", "/admin/gamecontent"),
                new MenuDetail("education_add", "교육 자료 등록", "/admin/education/add"),
                new MenuDetail("education_list", "교육 자료 조회", "/admin/education"),
                new MenuDetail("board_posts", "게시물 등록", "/admin/board/posts"),
                new MenuDetail("order_list", "매출 조회", "/admin/order"),
                new MenuDetail("member_list", "회원 조회", "/admin/member")));
    }

    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }
}
