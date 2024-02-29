package org.choongang.teacher.homework.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.member.MemberUtil;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.choongang.teacher.homework.controllers.RequestHomework;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.entities.QTrainingData;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkSaveService {

    private final HomeworkRepository homeworkRepository;
    private final MemberUtil memberUtil;
    private final SGInfoService sgInfoService;
    private final TrainingDataRepository trainingDataRepository;


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

            QTrainingData trainingData = QTrainingData.trainingData;
            BooleanBuilder andBuilder = new BooleanBuilder();
            andBuilder.and(trainingData.homework.num.eq(num));
            List<TrainingData> trainingDataList = (List<TrainingData>) trainingDataRepository.findAll(andBuilder);
            if (!trainingDataList.isEmpty()) {
                throw new AlertBackException("해당 숙제로 배포된 정보가 있어 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);
            }
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
