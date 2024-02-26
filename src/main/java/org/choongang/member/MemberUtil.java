package org.choongang.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.education.group.entities.JoinStudyGroup;
import org.choongang.education.group.services.joinStG.JoinSTGInfoService;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;
    private final JoinSTGInfoService joinSTGInfoService;

    public boolean isAdmin() {

        return isLogin() && getMember().getAuthority() == Authority.ADMIN;
        //return true;
    }

    public boolean isTeacher() {

        return isLogin() && getMember().getAuthority() == Authority.TEACHER;
    }

    public boolean isStudent() {
        return isLogin() && getMember().getAuthority() == Authority.STUDENT;
    }

    public boolean isUser() {

        return isLogin() && getMember().getAuthority() == Authority.USER;
//        return true;
    }

    /**
     * 로그인 회원이 가입 한 스터디 그룹이 있다면 true 없으면 false 리턴
     * @return
     */
    public boolean hasJoinStudyGroup (){

        List<JoinStudyGroup> list = joinSTGInfoService.getAll();
        boolean result_ = false;
        for(JoinStudyGroup j : list){
            if(j.getMember().getNum().equals(((Member)session.getAttribute("member")).getNum())){
                result_ = true;
                break;
            }
        }
        return result_;
    }




    public boolean isLogin() {

        return getMember() != null;
    }

    public Member getMember() {
        Member member = (Member) session.getAttribute("member");
        return member;
    }

    public static void clearLoginData(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }




}
