package org.choongang.teacher.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.service.HomeworkInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/get_table_data")
@RequiredArgsConstructor
public class RestHomeworkController {

    private final SGInfoService sgInfoService;
    private final HomeworkInfoService homeworkInfoService;

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
            tableData.append("<td><input type='checkbox' name='checkMember' th:id='*{'chkMB_' + num} th:value=*{num}>").append("</td>"); // 체크박스
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
            tableData.append("<td><input type='checkbox' name='checkHomework' th:id='*{'chkHW_' + num} th:value=*{num}>").append("</td>"); // 체크박스
            tableData.append("<td>").append(homework.getName()).append("</td>"); // 숙제명
            tableData.append("<td>").append(homework.getContent()).append("</td>"); // 내용
            tableData.append("<td>").append(homework.getStudyGroup().getName()).append("</td>"); // 학습그룹
            tableData.append("<td>").append(homework.getStudyLevel()).append("</td>"); // 숙제레벨
            tableData.append("<td>").append(homework.getDeadLine()).append("</td>"); // 제출마감
            tableData.append("</tr>");
        }

        return tableData.toString();
    }
}
