package org.choongang.teacher.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.controllers.GameContentSearch;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.commons.ListData;
import org.choongang.education.group.controllers.JoinStGroupSearch;
import org.choongang.education.group.entities.JoinStudyGroup;
import org.choongang.education.group.services.joinStG.JoinSTGInfoService;
import org.choongang.education.group.services.joinStG.JoinSTGSaveService;
import org.choongang.member.MemberUtil;
import org.choongang.teacher.group.controllers.RequestStGroup;
import org.choongang.teacher.group.controllers.StGroupSearch;
import org.choongang.teacher.group.entities.StudyGroup;
import org.choongang.teacher.group.services.stGroup.SGDeleteService;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.choongang.teacher.group.services.stGroup.SGSaveService;
import org.choongang.teacher.homework.controllers.RequestHomework;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.service.HomeworkInfoService;
import org.choongang.teacher.homework.service.HomeworkSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class
TeacherController {

    //group DI SSS //스터디 그룹 의존성
    private final SGSaveService sgSaveService;
    private final SGInfoService sgInfoService;
    private final SGDeleteService sgDeleteService;
    private final HttpSession session;
    private final GameContentInfoService gameContentInfoService;
    private final JoinSTGInfoService joinSTGInfoService;
    private final JoinSTGSaveService joinSTGSaveService;




    //group DI SSS




    ////////////////////////////////// homework
    private final HomeworkInfoService homeworkInfoService;
    private final HomeworkSaveService homeworkSaveService;
    ////////////////////////////

    private final MemberUtil memberUtil;


    // 그룹 목록
    /**
     * 스터디 그룹 목록
     * @param model
     * @param search
     * @return
     */
    @GetMapping("/group")
    public String groupList(Model model , @ModelAttribute StGroupSearch search) {
        commonProcess("list", model);



        ListData<StudyGroup> data = sgInfoService.getList(search);
        model.addAttribute("list" , data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "teacher/group/list";
    }

    /**
     * 스터디그룹 상세 (list -> detail)
     * @param num
     * @param model
     * @param search
     * @return
     */
    @GetMapping("/group/detail/{num}")
    public String detail(@PathVariable("num") Long num, Model model, @ModelAttribute StGroupSearch search){

        model.addAttribute("list" , sgInfoService.getList(search).getItems());
        model.addAttribute("item" , sgInfoService.getForm(num));
        model.addAttribute("members" , sgInfoService.getJoinMember(num));
        return "teacher/group/detail";
    }


    // 그룹 등록
    /**
     * 스터디그룹 등록 1. 게임 컨텐츠 설정
     * @param model
     * @param form
     * @return
     */
    @GetMapping("/group/add")
    public String addGroup1(Model model , @ModelAttribute RequestStGroup form , @ModelAttribute GameContentSearch search) {
        commonProcess("add", model);

        model.addAttribute("mode_" , "add1");

        ListData<GameContent> data = gameContentInfoService.getList(search);
        model.addAttribute("items" , data.getItems());
        model.addAttribute("pagination" , data.getPagination());

        return "teacher/group/add";
    }

    /**
     * 스터디 그룹 등록 2. 스터디 그룹 상세 설정
     * @param model
     * @param form
     * @param num
     * @return
     */
    @PostMapping("/group/add2")
    public String addGroup2(Model model , @ModelAttribute RequestStGroup form
            , @RequestParam(name = "num" , required = false) Long num,@ModelAttribute GameContentSearch search) {
        commonProcess("add", model);

        //스터디그룹 등록 1. 게임 컨텐츠 설정에서 게임 선택하지 않을경우
        if(num == null){
            model.addAttribute("mode_" , "add1");
            model.addAttribute("items" ,  gameContentInfoService.getList(search).getItems());
            model.addAttribute("pagination" , gameContentInfoService.getList(search).getPagination());
            model.addAttribute("emsg" , "게임 컨텐츠를 선택하세요");
            return "front/teacher/studyGroup/add";
        }

        //게임 선택 정상적으로 한 경우
        model.addAttribute("mode_" , "add2");

        //폼을 두 번 이동 해야 해서 session에 저장
        session.setAttribute("game" , gameContentInfoService.getById(num));

        return "teacher/group/add";
    }


    /**
     * 스터디 그룹 수정
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/group/edit/{num}")
    public String editGroup(@PathVariable("num") Long num, Model model) {
        commonProcess("edit", model);

        model.addAttribute("mode_" , "edit");
        RequestStGroup stg = sgInfoService.getForm(num);

        model.addAttribute("requestStGroup" , stg);
        session.setAttribute("game" , gameContentInfoService.getById(stg.getGameContentNum()));

        return "teacher/group/edit";
    }


    /**
     * 스터디 그룹 생성/수정
     * @param form
     * @param errors
     * @param model
     * @return
     */
    @PostMapping("/group/save")
    public String saveGroup(@Valid RequestStGroup form , Errors errors , Model model) {

        //스터디그룹 입력항목 누락 시
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(System.out::println);
            model.addAttribute("mode_" , "add2");
            return "front/teacher/studyGroup/add";
        }

        sgSaveService.save(form);

        //저장 후session 비워주기
        session.removeAttribute("game");

        return "redirect:/teacher/group"; // 추가, 수정 완료 후 그룹 목록으로 이동
    }

    /**
     * 선택삭제 (여러개 동시)
     * @param chks
     * @param model
     * @return
     */
    @DeleteMapping
    public String deletes(@RequestParam(name = "chk" ) List<Long> chks ,Model model){
        for(Long n : chks){
            sgDeleteService.delete(n);
        }
        return "redirect:/teacher/group";
    }

    /**
     * 단일 삭제
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/group/delete/{num}")
    public String delete(@PathVariable("num") Long num , Model model){
        sgDeleteService.delete(num);
        return "redirect:/teacher/group";
    }



    /**
     * ( 교육자가 가입 승인하는 )
     * 가입 신청 목록
     * @param model
     * @param search
     * @return
     */
    @GetMapping("/group/accept")
    public String acceptGroup(Model model , @ModelAttribute JoinStGroupSearch search) {
        commonProcess("accept", model);
        //가입 승인 대기 / 완료 목록
        ListData<JoinStudyGroup> data = joinSTGInfoService.getList(search);
        model.addAttribute("list" , data.getItems());
        model.addAttribute("pagination" , data.getPagination());

        return "teacher/group/accept";
    }

    /**
     * 스터디그룹 가입 승인 처리
     * @param model
     * @return
     */

    @PostMapping("/group/accept")
    public String acceptGroupPs(Model model ,  @RequestParam(name = "chk" ) List<Long> chks) {
        commonProcess("accept", model);
        //가입 승인 처리
        joinSTGSaveService.accept(chks);

        return "redirect:/teacher/group/accept";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    /** 숙제 리스트
     *
     * @param model
     * @return
     */
    @GetMapping("/homework")
    public String homeworkList(Model model) {
        commonProcess("homework_list", model);

        // 내가(한 교육자가) 담당하는 그룹만 조회할 수 있도록
//        Member member = memberUtil.getMember();
//        if (member == null) {
//            return "redirect:/member/login";
//        }

//        List<Homework> items = homeworkInfoService.getList(member.getNum());

        List<Homework> items = homeworkInfoService.getList();
        model.addAttribute("items", items);

        return "teacher/homework/list";
    }

    /** 숙제 생성
     *
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/homework/add")
    public String addHomework(@ModelAttribute RequestHomework form, Model model) {
        commonProcess("homework_add", model);

        return "teacher/homework/add";
    }

    /** 숙제 수정
     *
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/homework/edit/{num}")
    public String editHomework(@PathVariable("num") Long num, Model model) {
        commonProcess("homework_edit", model);

        RequestHomework form = homeworkInfoService.getForm(num);

        model.addAttribute("requestForm", form);

        return "teacher/homework/edit";
    }

    /** 숙제 작성/수정 처리
     *
     * @param form
     * @param model
     * @return
     */
    @PostMapping("/homework/save")
    public String saveHomework(@Valid RequestHomework form, Model model) {

        homeworkSaveService.save(form);

        return "redirect:/teacher/homework";
    }

    /** 숙제 배포 (작업중)
     *
     * @param model
     * @return
     */
    @GetMapping("/homework/distribute")
    public String distributeHomework(@ModelAttribute StGroupSearch search, Model model) {
        commonProcess("distribute", model);

        /*
        학습그룹 조회, 숙제 조회
        체크박스로 체크하여 숙제를 해당 인원들에게 전송.
         */
//        Member member = memberUtil.getMember();
//        if (member == null) {
//            return "redirect:/member/login";
//        }
        //        List<Homework> items = homeworkInfoService.getList(member.getNum()); // 교육자가 작성한 숙제
        List<Homework> items = homeworkInfoService.getList(); // 임시 전체조회


//        ListData<StudyGroup> data = sgInfoService.getList(search);
//
//        model.addAttribute("list" , data.getItems());
//        model.addAttribute("pagination", data.getPagination());

        model.addAttribute("items", items);

        return "teacher/homework/distribute";
    }


    /** 숙제 배포 처리 (예정)
     *
     * @param model
     * @return
     */
    @PostMapping("/homework/distribute")
    public String distributeHomeworkPs(Model model) {
        commonProcess("distribute", model);

        return "redirect:/teacher/homework";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle = "교육자마당";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("add")) {
            pageTitle = "학습 그룹 등록::" + pageTitle;
        } else if (mode.equals("edit")) {
            pageTitle = "학습 그룹 수정::" + pageTitle;
        } else if (mode.equals("list")) {
            pageTitle = "학습 그룹 조회::" + pageTitle;
        } else if (mode.equals("homework_add")) {
            pageTitle = "숙제 생성::" + pageTitle;
        } else if (mode.equals("homework_edit")) {
            pageTitle = "숙제 수정::" + pageTitle;
        } else if (mode.equals("distribute")) {
            pageTitle = "숙제 배포::" + pageTitle;
        } else if (mode.equals("homework_list")) {
            pageTitle = "숙제 학습 진도 조회::" + pageTitle;
        } else if (mode.equals("accept")) {
            pageTitle = "회원 그룹 가입 승인::" + pageTitle;
        }

        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }
}
