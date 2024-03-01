package org.choongang.teacher.homework.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.choongang.teacher.homework.controllers.RequestHomework;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeworkSaveService {

    private final HomeworkRepository homeworkRepository;
    private final MemberUtil memberUtil;
    private final SGInfoService sgInfoService;

    /** 스터디그룹에 숙제 등록
     *
     * @param form
     */
    public void save(RequestHomework form) {
        String mode = form.getMode();
        Long num = form.getNum();

        Homework homework = null;
        if ("edit".equals(mode)) {
            homework = homeworkRepository.findById(num).orElseThrow();
        } else {
            homework = new Homework();
        }

        homework.setName(form.getName());
        homework.setContent(form.getContent());
        homework.setStudyGroup(sgInfoService.getById(form.getStudyGroupNum()));
        homework.setStudyLevel(form.getStudyLevel());
        homework.setDeadLine(form.getDeadLine());

        /* 나중에 추가할 내용 S */
        homework.setMember(memberUtil.getMember());

        /* 나중에 추가할 내용 E */

        homeworkRepository.saveAndFlush(homework);
    }
}
