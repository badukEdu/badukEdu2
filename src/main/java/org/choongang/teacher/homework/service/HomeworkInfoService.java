package org.choongang.teacher.homework.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.teacher.homework.controllers.RequestHomework;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.entities.QHomework;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkInfoService {
    private final HomeworkRepository homeworkRepository;
    private final MemberUtil memberUtil;

    /** 숙제 전체조회
     *
     * @return
     */
    public List<Homework> getList() {
        List<Homework> items = homeworkRepository.findAll();

        return items;
    }

    /** 교육자별 숙제 조회
     *
     * @param memberNum
     * @return
     */
    public List<Homework> getList(Long memberNum) {

        QHomework homework = QHomework.homework;

        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(homework.member.num.eq(memberUtil.getMember().getNum()));
//
//        List<Homework> items = (List<Homework>) homeworkRepository.findAll(andBuilder, Sort.by(Sort.Order.asc("createdAt")));
        List<Homework> items = (List<Homework>) homeworkRepository.findAll(andBuilder);

        return items;
    }


    /** 숙제 하나 조회
     *
     * @param num
     * @return
     */
    public RequestHomework getForm(Long num) {
        Homework homework = homeworkRepository.findById(num).orElseThrow();
        RequestHomework form = new ModelMapper().map(homework, RequestHomework.class);
        return form;
    }



    /**
     *  해당 스터디그룹에 등록된 숙제 목록 리턴
     * @param num -> 스터디그룹 num
     * @return
     */
    public List<Homework> getHomeworks(Long num) {

        List<Homework> homeworks = new ArrayList<>();
        homeworks = homeworkRepository.getByStudyGroupNum(num);

        return homeworks;
    }
}
