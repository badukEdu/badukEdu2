package org.choongang.teacher.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/get_table_data")
@RequiredArgsConstructor
public class RestHomeworkController {

    private final SGInfoService sgInfoService;

    @GetMapping
    @ResponseBody
    public String getTableData(@RequestParam("option") String selectedOption) {

        if (selectedOption.equals("선택")) {
            return "<tr><td colspan='4'>학습 그룹을 선택해주세요.</td></tr>";
        }
        // 선택된 학습 그룹의 데이터를 조회
        List<Member> members = sgInfoService.getJoinMember(Long.valueOf(selectedOption));

        if (members.isEmpty()) {
            return "<tr><td colspan='4'>그룹에 속한 학습자가 없습니다.</td></tr>";
        }
        // 조회된 데이터를 HTML 형식으로 생성
        StringBuilder tableData = new StringBuilder();

        for (Member member : members) {
            tableData.append("<tr>");
            tableData.append("<td><input type='checkbox' name='chk' th:id='*{'chk_' + num} th:value=*{num}>").append("</td>"); // 체크박스
            tableData.append("<td>").append(member.getName()).append("</td>"); // 학습자명
            tableData.append("<td>").append(member.getTel()).append("</td>"); // 전화번호
            tableData.append("<td>").append(member.getLevels()).append("</td>"); // 현재 레벨
            tableData.append("</tr>");
        }

        return tableData.toString();
    }
}
