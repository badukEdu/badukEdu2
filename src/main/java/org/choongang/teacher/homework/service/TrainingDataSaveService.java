package org.choongang.teacher.homework.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.teacher.homework.controllers.RequestQuestionAnswer;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingDataSaveService {

    private final HomeworkInfoService homeworkInfoService;
    private final TrainingDataRepository trainingDataRepository;
    private final MemberRepository memberRepository;

    /**
     *
     * @param trainingData
     */
    public void save(TrainingData trainingData) {


/*
        if (form == null && form.getNum() == null) {
            data = new TrainingData();
        } else {
            data.setNum(form.getNum());
        }

        homeworkInfoService.getForm(num);

        form.setSDate(LocalDateTime.now());
//        form.set


*/
        // 작성중..
        trainingDataRepository.saveAndFlush(trainingData);
    }

    public void saveScore(List<Long> chks, List<Long> scores) {

        for (int i = 0; i < chks.size(); i++) {
            TrainingData trainingData = trainingDataRepository.findById(chks.get(i)).orElseThrow();
            if (scores.get(i) instanceof Long) {
                continue;
            }
            trainingData.setScore(scores.get(i));

            if (scores.get(i) >= 1) { // 보통 이상의 평가를 받았을 때
                Member member = memberRepository.findById(trainingData.getMember().getNum()).orElseThrow();
                // 학습자의 레벨 <= 숙제의 레벨
                member.setLevels(trainingData.getHomework().getStudyLevel());
                memberRepository.save(member);
            }

            trainingDataRepository.save(trainingData);
        }
        trainingDataRepository.flush();
        memberRepository.flush();;
    }

    public void saveQuestionAnswer(RequestQuestionAnswer form) {

        TrainingData trainingData = trainingDataRepository.findById(form.getNum()).orElseThrow();

        trainingData.setQuestionAnswer(form.getQuestionAnswer());

        trainingDataRepository.saveAndFlush(trainingData);
    }
}
