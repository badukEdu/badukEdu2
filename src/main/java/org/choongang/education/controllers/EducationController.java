package org.choongang.education.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.education.group.controllers.JoinStGroupSearch;
import org.choongang.education.group.entities.JoinStudyGroup;
import org.choongang.education.group.services.joinStG.JoinSTGInfoService;
import org.choongang.education.group.services.joinStG.JoinSTGSaveService;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.controllers.StGroupSearch;
import org.choongang.teacher.group.entities.StudyGroup;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController implements ExceptionProcessor  {

    private final SGInfoService sgInfoService;
    private final JoinSTGSaveService joinSTGSaveService;
    private final JoinSTGInfoService joinSTGInfoService;
    private final HttpSession session;

    ////////////////////////////homework

    private final EduTrainingDataInfoService eduTrainingDataInfoService;
    private final EduTrainingDataSaveService trainingDataSaveService;

    // 현재 신청중인 목록
    /**
     *
     * 가입 가능한 스터디그룹 가입 승인 대기 목록
     * @param model
     * @return
     */
    @GetMapping
    public String list(Model model , @ModelAttribute JoinStGroupSearch search) {
        commonProcess("list", model);

        search.setType("wait");

        ListData<JoinStudyGroup> data = joinSTGInfoService.getList(search);
        model.addAttribute("list" , data.getItems());
        model.addAttribute("pagination" , data.getPagination());

        return "education/list";
    }

    // 학습그룹 신청 가능한 스터디그룹 목록
    /**
     * ( 학생이 가입 신청 하는)
     * 가입 가능한 스터디그룹 목록
     * @param model
     * @param search
     * @return
     */
    @GetMapping("/join")
    public String join(Model model , @ModelAttribute StGroupSearch search) {
        commonProcess("join", model);

        search.setType("joinstg");
        ListData<StudyGroup> data = sgInfoService.getList(search);

        //validstg -> 이미 가입 한 스터디그룹은 목록에서 제외 / andBuilder로 처리한 것이 아니라 pagination 사용 불가
        model.addAttribute("list" , validstg(data.getItems()) );
        //model.addAttribute("pagination", data.getPagination());

        return "education/join";
    }

    // 신청 처리
    /**
     * 가입신청
     * @param model
     * @param search
     * @return
     */
    @PostMapping("/apply")
    public String apply(Model model , @ModelAttribute StGroupSearch search ,
                        @RequestParam(name = "chk" ) List<Long> chks ) {
        commonProcess("join", model);
        //가입 신청 내역 저장(칼럼 : accept -> (미승인)false == 0)
        joinSTGSaveService.save(chks);
        return "redirect:/education/join";
    }

    // 신청 내용 취소
    @GetMapping("/cancel/{num}")
    public String cancel(@PathVariable("num") Long num, Model model) {
        commonProcess("cancel", model);

        return "redirect:/education";
    }

    // 신청 내용 상세
    @GetMapping("/group/{num}")
    public String view(@PathVariable("num") Long num, Model model) {
        commonProcess("view", model);

        return "education/view";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "학습서비스";
        List<String> addScript = new ArrayList<>();
        if (mode.equals("list")) {
            pageTitle = "신청목록::" + pageTitle;
        } else if (mode.equals("join")) {
            pageTitle = "학습그룹 가입신청::" + pageTitle ;
            addScript.add("education/form");
        } else if (mode.equals("cancal")) {
            pageTitle = "신청취소::" + pageTitle;
        } else if (mode.equals("view")) {
            pageTitle = "신청내용::" + pageTitle;
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", "education_" + mode);
    }

    /**
     * 현재 로그인 회원이 이미 가입 신청한 스터디그룹은 목록에서 제외 처리해주는 메서드
     * @param list
     * @return
     */
    public List<StudyGroup> validstg(List<StudyGroup> list){

        //가입 승인 대기 / 완료 목록
        List<JoinStudyGroup> joinList = joinSTGInfoService.getAll();

        //로그인 회원 정보
        Member member = (Member)session.getAttribute("member");


        for(JoinStudyGroup jsg : joinList){
            if(member.getNum().equals(jsg.getMember().getNum())) {
                for(int k=list.size()-1; k>=0; k--){
                    if(list.get(k).getNum().equals(jsg.getStudyGroup().getNum())){
                        list.remove(sgInfoService.getById(jsg.getStudyGroup().getNum()));
                    }
                }
            }
        }
        return list;
    }
}
