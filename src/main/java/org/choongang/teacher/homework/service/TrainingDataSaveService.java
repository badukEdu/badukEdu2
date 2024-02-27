package org.choongang.teacher.homework.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.teacher.homework.controllers.RequestQuestionAnswer;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.entities.QTrainingData;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingDataSaveService {

    private final HomeworkInfoService homeworkInfoService;
    private final TrainingDataRepository trainingDataRepository;
    private final MemberRepository memberRepository;
    private final MemberUtil memberUtil;
    private final HomeworkRepository homeworkRepository;

    /**
     *
     *
     */
    public void save(List<Long> checkedHomeworks, List<Long> checkedMembers) {

        QTrainingData trainingData = QTrainingData.trainingData;
        BooleanBuilder andBuilder = new BooleanBuilder();

        // 없다면 new
        TrainingData form = null;
        Homework homework = null;
        Member member = null;
        // 체크된 숙제를 // 각 체크된 그룹 멤버에게 배포
        for (Long chkHW : checkedHomeworks) {

            // trainingData 중에서 나에게 배포된 것들 탐색
            andBuilder.and(trainingData.member.eq(memberUtil.getMember()));
            // 그 중 현재 선택된 homework와 동일한 것 탐색
            andBuilder.and(trainingData.homework.num.eq(chkHW));

            List<TrainingData> trainingDataList= (List<TrainingData>) trainingDataRepository.findAll(andBuilder);
            System.out.println(trainingDataList);
            // 학습자가 가진 trainingData 중 homework 정보 중에서 동일한 homework num이 있는 것은 패스
            if (!trainingDataList.isEmpty()) {
                continue;
            }

            homework = homeworkRepository.findById(chkHW).orElseThrow();
            for (Long chkMB : checkedMembers) {
                form = new TrainingData();
                member = memberRepository.findById(chkMB).orElseThrow();
                form.setHomework(homework);
                form.setMember(member);
                trainingDataRepository.save(form);
            }
        }

        trainingDataRepository.flush();
    }

    public void saveScore(List<Long> chks, List<Long> scores) {

        for (int i = 0; i < chks.size(); i++) {
            TrainingData trainingData = trainingDataRepository.findById(chks.get(i)).orElseThrow();
            if (scores.get(i) < 0) {
                continue;
            }
            trainingData.setScore(scores.get(i));

            if (scores.get(i) >= 1) { // 보통 이상의 평가를 받았을 때
                Member member = memberRepository.findById(trainingData.getMember().getNum()).orElseThrow();
                // 학습자의 레벨 < 숙제레벨일 때
                if (member.getLevels() < trainingData.getHomework().getStudyLevel()) {
                    // 학습자의 레벨 = 숙제의 레벨 입력
                    member.setLevels(trainingData.getHomework().getStudyLevel());
                    memberRepository.save(member);
                }
            }

            trainingDataRepository.save(trainingData);
        }
        trainingDataRepository.flush();
        memberRepository.flush();
    }

    public void saveQuestionAnswer(RequestQuestionAnswer form) {

        TrainingData trainingData = trainingDataRepository.findById(form.getNum()).orElseThrow();

        trainingData.setQuestionAnswer(form.getQuestionAnswer());

        trainingDataRepository.saveAndFlush(trainingData);
    }
}
