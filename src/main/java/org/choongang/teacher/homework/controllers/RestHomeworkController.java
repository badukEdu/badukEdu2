package org.choongang.teacher.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.service.HomeworkInfoService;
import org.choongang.teacher.homework.service.TrainingDataInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/get_table_data")
@RequiredArgsConstructor
public class RestHomeworkController {

    private final SGInfoService sgInfoService;
    private final HomeworkInfoService homeworkInfoService;
    private final TrainingDataInfoService trainingDataInfoService;

    @GetMapping("/member")
    @ResponseBody
    public String getMemberData(@RequestParam("option") String selectedOption) {

        if (selectedOption.equals("선택")) {
            return "<tr><td colspan='4'>학습 그룹을 선택해주세요.</td></tr>";
        }
        // 선택된 학습 그룹의 멤버 데이터를 조회
        List<Member> members = sgInfoService.getJoinMember(Long.valueOf(selectedOption));

        if (members.isEmpty()) {
            return "<tr><td colspan='4'>그룹에 등록된 학습자가 없습니다.</td></tr>";
        }
        // 조회된 데이터를 HTML 형식으로 생성
        StringBuilder tableData = new StringBuilder();

        for (Member member : members) {
            tableData.append("<tr>");
            tableData.append("<td><input type='checkbox' name='checkMember' value='").append(member.getNum()).append("' data-studyLevel='").append(member.getLevels()).append("'></td>"); // 체크박스
            tableData.append("<td>").append(member.getName()).append("</td>"); // 학습자명
            tableData.append("<td>").append(member.getTel()).append("</td>"); // 전화번호
            tableData.append("<td>").append(member.getLevels()).append("</td>"); // 현재 레벨
            tableData.append("</tr>");
        }

        return tableData.toString();
    }

    @GetMapping("/homework")
    @ResponseBody
    public String getHomeworkData(@RequestParam("option") String selectedOption) {

        if (selectedOption.equals("선택")) {
            return "<tr><td colspan='4'>학습 그룹을 선택해주세요.</td></tr>";
        }
        // 선택된 학습 그룹의 숙제 데이터를 조회
        List<Homework> homeworks = homeworkInfoService.getHomeworks(Long.valueOf(selectedOption));

        if (homeworks.isEmpty()) {
            return "<tr><td colspan='4'>그룹에 등록된 숙제가 없습니다.</td></tr>";
        }
        // 조회된 데이터를 HTML 형식으로 생성
        StringBuilder tableData = new StringBuilder();

        for (Homework homework : homeworks) {
            tableData.append("<tr>");
            tableData.append("<td><input type='checkbox' name='checkHomework' value='").append(homework.getNum()).append("' data-deadLine='").append(homework.getDeadLine()).append("' data-homeworkLevel='").append(homework.getStudyLevel()).append("' onchange='checkLevels();'></td>"); // 체크박스
            tableData.append("<td>").append(homework.getName()).append("</td>"); // 숙제명
            tableData.append("<td>").append(homework.getContent()).append("</td>"); // 내용
            tableData.append("<td>").append(homework.getStudyGroup().getName()).append("</td>"); // 학습그룹
            tableData.append("<td>").append(homework.getStudyLevel()).append("</td>"); // 숙제레벨
            tableData.append("<td>").append(homework.getDeadLine()).append("</td>"); // 제출마감
            tableData.append("</tr>");
        }

        return tableData.toString();
    }

    @GetMapping("/trainingData")
    @ResponseBody
    public String getTrainingData(@RequestParam("option") String selectedOption) {

//        if (selectedOption.equals("선택")) {
//            return "<tr><td>평가할 숙제를 선택해주세요.</td></tr>";
//        }

        // 선택된 숙제의 trainingData를 조회
        List<TrainingData> trainingDataList = trainingDataInfoService.getlist(Long.valueOf(selectedOption)); // 미처리된 메서드
        if (trainingDataList.isEmpty()) {
            return "<tr><td colspan='6'>숙제를 전송한 정보가 없습니다.</td></tr>";
        }
        StringBuilder tableData = new StringBuilder();

        for (TrainingData trainingData : trainingDataList) {
            tableData.append("<input type='hidden' name='chk' value='").append(trainingData.getNum()).append("'>");
            tableData.append("<tr>");
            tableData.append("<td>").append(trainingData.getMember().getName()).append("</td>"); // 학습자명
            tableData.append("<td>").append(trainingData.getCreatedAt()).append("</td>"); // 숙제 배포일자
            tableData.append("<td>").append(trainingData.getHomeworkAnswer()).append("</td>"); // 학습자 작성 정답
            tableData.append("<td onclick='answerPopup(").append(trainingData.getNum()).append(")'>").append(trainingData.getQuestion()).append("</td>"); // 질문사항
            tableData.append("<td>").append(trainingData.getSendDate()).append("</td>"); // 학습자 제출일자
//            tableData.append("<td>").append(trainingData.getScore()).append("</td>"); // 평가
            tableData.append("<td width='100'><label><select name='score'>");
            if (trainingData.getScore() == null) {
                tableData.append("<option value='-1' selected>선택</option>" +
                        "         <option value='0'>미흡</option>" +
                        "         <option value='1'>보통</option>" +
                        "         <option value='2'>우수</option>");
            } else if (trainingData.getScore() == 0) {
                tableData.append("<option value='-1'>선택</option>" +
                        "         <option value='0' selected>미흡</option>" +
                        "         <option value='1'>보통</option>" +
                        "         <option value='2'>우수</option>");
            } else if (trainingData.getScore() == 1) {
                tableData.append("<option value='-1'>선택</option>" +
                        "         <option value='0'>미흡</option>" +
                        "         <option value='1' selected>보통</option>" +
                        "         <option value='2'>우수</option>");
            } else if (trainingData.getScore() == 2) {
                tableData.append("<option value='-1'>선택</option>" +
                        "         <option value='0'>미흡</option>" +
                        "         <option value='1'>보통</option>" +
                        "         <option value='2' selected>우수</option>");
            }
            tableData.append("</select></label></td>");
            tableData.append("</tr>");
        }


        return tableData.toString();
    }
}
