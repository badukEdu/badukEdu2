package org.choongang.teacher.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.controllers.GameContentSearch;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.admin.order.controllers.OrderSearch;
import org.choongang.admin.order.entities.OrderItem;
import org.choongang.admin.order.service.OrderInfoService;
import org.choongang.commons.ListData;
import org.choongang.education.group.controllers.JoinStGroupSearch;
import org.choongang.education.group.entities.JoinStudyGroup;
import org.choongang.education.group.services.joinStG.JoinSTGInfoService;
import org.choongang.education.group.services.joinStG.JoinSTGSaveService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.teacher.group.controllers.RequestStGroup;
import org.choongang.teacher.group.controllers.StGroupSearch;
import org.choongang.teacher.group.entities.StudyGroup;
import org.choongang.teacher.group.services.stGroup.SGDeleteService;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.choongang.teacher.group.services.stGroup.SGSaveService;
import org.choongang.teacher.homework.controllers.RequestHomework;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.choongang.teacher.homework.service.HomeworkDeleteService;
import org.choongang.teacher.homework.service.HomeworkInfoService;
import org.choongang.teacher.homework.service.HomeworkSaveService;
import org.choongang.teacher.homework.service.TrainingDataSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
@SessionAttributes({"requestHomework", "list", "pagination", "items"})
public class TeacherController {

    //group DI SSS //스터디 그룹 의존성
    private final SGSaveService sgSaveService;
    private final SGInfoService sgInfoService;
    private final SGDeleteService sgDeleteService;
    private final HttpSession session;
    private final GameContentInfoService gameContentInfoService;
    private final JoinSTGInfoService joinSTGInfoService;
    private final JoinSTGSaveService joinSTGSaveService;
    private final OrderInfoService orderInfoService;
    //group DI SSS




    ////////////////////////////////// homework
    private final HomeworkInfoService homeworkInfoService;
    private final HomeworkSaveService homeworkSaveService;
    private final HomeworkDeleteService homeworkDeleteService;
    private final TrainingDataSaveService trainingDataSaveService;
    private final HomeworkRepository homeworkRepository;
    private final MemberRepository memberRepository;
    private final TrainingDataRepository trainingDataRepository;
    ////////////////////////////

    private final MemberUtil memberUtil;


    @ModelAttribute("requestHomework")
    public RequestHomework requestHomework() {
        return new RequestHomework();
    }

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
        commonProcess("detail", model);
        model.addAttribute("list" , sgInfoService.getList(search).getItems());
        model.addAttribute("item" , sgInfoService.getForm(num));
        model.addAttribute("members" , sgInfoService.getJoinMember(num));
        model.addAttribute("jlist" , joinSTGInfoService.getJoin(num));
        return "teacher/group/detail";
    }

    /**
     * 스터디그룹 상세 (detail -> detail /
     * 서브메뉴에서 바로 detail 접근 시 등록된 스터디 그룹이 없다면 목록으로, 있다면 첫 번재 스터디 그룹 보여줌)
     * @param num
     * @param model
     * @param search
     * @return
     */
    @GetMapping("/group/detail")
    public String detail2(@RequestParam(value = "num" ,required = false) Long num, Model model, @ModelAttribute StGroupSearch search){

        if(num == null || num == 0){
            if(sgInfoService.getList(search).getItems().isEmpty()){
                commonProcess("list", model);
                ListData<StudyGroup> data = sgInfoService.getList(search);
                model.addAttribute("list" , data.getItems());
                model.addAttribute("pagination", data.getPagination());
                model.addAttribute("emsg" , "학습 그룹을 등록해야 상세보기가 가능합니다.");
                return "teacher/group/list";
            } else if (!sgInfoService.getList(search).getItems().isEmpty()) {

                num = sgInfoService.getList(search).getItems().get(0).getNum();
                commonProcess("detail", model);
                model.addAttribute("list" , sgInfoService.getList(search).getItems());
                model.addAttribute("item" , sgInfoService.getForm(num));
                model.addAttribute("members" , sgInfoService.getJoinMember(num));
                model.addAttribute("jlist" , joinSTGInfoService.getJoin(num));
                return "teacher/group/detail";
            }
        }

        commonProcess("detail", model);
        model.addAttribute("list" , sgInfoService.getList(search).getItems());
        model.addAttribute("item" , sgInfoService.getForm(num));
        model.addAttribute("members" , sgInfoService.getJoinMember(num));
        model.addAttribute("jlist" , joinSTGInfoService.getJoin(num));
        return "teacher/group/detail";
    }


    // 그룹 등록
    /**
     * 스터디그룹 등록 1. 게임 컨텐츠 설정
     * @param model
     * @return
     */
    @GetMapping("/group/add")
    public String addGroup1(/*Model model , @ModelAttribute RequestStGroup form , @ModelAttribute GameContentSearch search*/
            @ModelAttribute OrderSearch search,
            Model model) {
        commonProcess("add", model);

        model.addAttribute("mode_" , "add1");

        /*
        ListData<GameContent> data = gameContentInfoService.getList(search , true);
        model.addAttribute("items" , data.getItems());
        model.addAttribute("pagination" , data.getPagination());
        */
        ListData<OrderItem> data = orderInfoService.getList(search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());
        model.addAttribute("acceptChange" , true);

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
            , @RequestParam(name = "num" , required = false) Long num,@ModelAttribute OrderSearch search) {
        commonProcess("add", model);

        //스터디그룹 등록 1. 게임 컨텐츠 설정에서 게임 선택하지 않을경우
        if(num == null){
            model.addAttribute("mode_" , "add1");
            ListData<OrderItem> data = orderInfoService.getList(search);
            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());
            model.addAttribute("emsg" , "게임 컨텐츠를 선택하세요");
            return "teacher/group/add";
        }

        //게임 선택 정상적으로 한 경우
        model.addAttribute("mode_" , "add2");
        model.addAttribute("acceptChange" , true);
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

        boolean acceptChange = true;

        if(!sgInfoService.getJoinMember(num).isEmpty()){
            acceptChange = false;
        }

        model.addAttribute("acceptChange" , acceptChange);
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
            model.addAttribute("acceptChange" , true);
            return "teacher/group/add";
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
            if(sgInfoService.hasMember(n)){
                return "redirect:/teacher/group";
            }
        }
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
        if(sgInfoService.hasMember(num)){
            return "redirect:/teacher/group";
        }
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
    public String acceptGroupPs(Model model ,  @RequestParam(name = "chk" , required = false) List<Long> chks,
     @ModelAttribute JoinStGroupSearch search) {
        commonProcess("accept", model);

        if(chks == null || chks.isEmpty()){
            commonProcess("accept", model);
            //가입 승인 대기 / 완료 목록
            ListData<JoinStudyGroup> data = joinSTGInfoService.getList(search);
            model.addAttribute("list" , data.getItems());
            model.addAttribute("pagination" , data.getPagination());
            model.addAttribute("emsg" , "승인할 스터디그룹을 선택하세요");
            return "teacher/group/accept";
        }


        //가입 승인 처리
        joinSTGSaveService.accept(chks);

        return "redirect:/teacher/group/accept";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////


    /** 숙제 생성/조회
     *
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/homework/add")
    public String addHomework(@ModelAttribute RequestHomework form, @ModelAttribute StGroupSearch search, Model model) {
        commonProcess("homework_add", model);


        ListData<StudyGroup> data = sgInfoService.getList(search);

        model.addAttribute("list" , data.getItems());
        model.addAttribute("pagination", data.getPagination());

        Member member = memberUtil.getMember();
        if (member == null) {
            return "redirect:/member/login";
        }
        List<Homework> items = homeworkInfoService.getList(member.getNum()); // 교육자가 작성한 숙제

        model.addAttribute("items", items);


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
        form.setMode("edit");

        model.addAttribute("requestHomework", form);

        return "teacher/homework/edit";
    }

    /** 숙제 작성/수정 처리
     *
     * @param form
     * @param model
     * @return
     */
    @PostMapping("/homework/save")
    public String saveHomework(@Valid RequestHomework form, Errors errors, Model model, SessionStatus status) {

        if (errors.hasErrors()) {
            return "teacher/homework/" + form.getMode();
        }
        homeworkSaveService.save(form);


        status.setComplete();

        return "redirect:/teacher/homework/add";
    }

    /** 숙제 삭제 처리
     *
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/homework/delete/{num}")
    public String deleteHomework(@PathVariable("num") Long num, Model model) {

        homeworkDeleteService.delete(num);

        return "redirect:/teacher/homework/add";
    }

    /** 숙제 배포
     *
     * @param model
     * @return
     */
    @GetMapping("/homework/distribute")
    public String distributeHomework(@ModelAttribute StGroupSearch search, Model model) {
        commonProcess("distribute", model);

        Member member = memberUtil.getMember();
        if (member == null) {
            return "redirect:/member/login";
        }
        List<Homework> items = homeworkInfoService.getList(member.getNum()); // 교육자가 작성한 숙제

        model.addAttribute("items", items);

        ListData<StudyGroup> data = sgInfoService.getList(search);

        model.addAttribute("list" , data.getItems());
        model.addAttribute("pagination", data.getPagination());


        return "teacher/homework/distribute";
    }


    /** 숙제 배포 처리
     *
     * @param checkedHomeworks -> 체크된 학습그룹 숙제
     * @param checkedMembers -> 체크된 학습그룹 멤버
     * @param model
     * @return
     */
    @PostMapping("/homework/distribute")
    public String distributeHomeworkPs(@RequestParam("checkHomework") List<Long> checkedHomeworks,
                                       @RequestParam("checkMember") List<Long> checkedMembers,
                                       Model model) {
        commonProcess("distribute", model);

        TrainingData form = null;
        Homework homework = null;
        Member member = null;
        // 체크된 숙제를
        for (Long chkHW : checkedHomeworks) {
            // 각 체크된 그룹 멤버에게 배포
            homework = homeworkRepository.findById(chkHW).orElseThrow();
            for (Long chkMB : checkedMembers) {
                form = new TrainingData();
                member = memberRepository.findById(chkMB).orElseThrow();
                form.setHomework(homework);
                form.setMember(member);
                trainingDataSaveService.save(form);
            }
        }

        return "redirect:/teacher/homework/distribute";
    }


    /** 숙제 평가 페이지
     *
     * @param model
     * @return
     */
    @GetMapping("/homework/assess")
    public String homeworkAssess(Model model) {
        commonProcess("assess", model);

        Member member = memberUtil.getMember();
        if (member == null) {
            return "redirect:/member/login";
        }
        List<Homework> items = homeworkInfoService.getList(member.getNum()); // 교육자가 작성한 숙제

        model.addAttribute("items", items);

        List<TrainingData> trainingDataList = trainingDataRepository.findAll();
        model.addAttribute("trainingDataList", trainingDataList);

        return "teacher/homework/assess";
    }

    /** 숙제 평가 처리
     *
     * @param chks -> 평가 대상
     * @param scores -> 평가 점수 (0:미흡, 1:보통, 2:우수)
     * @param model
     * @return
     */
    @PostMapping("homework/assess")
    public String homeworkAssessPs(@RequestParam("chk") List<Long> chks,
                                   @RequestParam("score") List<Long> scores,
                                   Model model) {

        for (int i = 0; i < chks.size(); i++) {
            trainingDataSaveService.saveScore(chks,scores);
        }

        return "redirect:/teacher/homework/assess";
    }

    /** 숙제 답변 처리 (미처리)
     *
     * @return
     */
    @PostMapping("homework/assessDetail")
    public String homeworkAssessDetailPs() {
        return "redirect:/teacher/homework/assess";
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
            pageTitle = "숙제 생성/조회::" + pageTitle;
            addScript.add("homework/" + mode);
        } else if (mode.equals("homework_edit")) {
            pageTitle = "숙제 수정::" + pageTitle;
        } else if (mode.equals("distribute")) {
            pageTitle = "숙제 배포::" + pageTitle;
            addScript.add("homework/" + mode);
        } else if (mode.equals("assess")) {
            pageTitle = "숙제 학습 진도 조회::" + pageTitle;
            addScript.add("homework/" + mode);
        } else if (mode.equals("accept")) {
            pageTitle = "회원 그룹 가입 승인::" + pageTitle;
        }else if (mode.equals("detail")) {
            pageTitle = "스터디그룹 상세::" + pageTitle;
        }

        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }
}
