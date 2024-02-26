package org.choongang.teacher.homework.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.teacher.homework.entities.QTrainingData;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingDataInfoService {

    private final TrainingDataRepository trainingDataRepository;

    /** 해당 숙제 정보를 담고 있는 trainingData 리스트 반환
     *
     * @return
     */
    public List<TrainingData> getlist(Long num) {
        // homework의 num이 num인 trainingData 리스트 반환
        QTrainingData trainingData = QTrainingData.trainingData;

        BooleanBuilder andBuilder = new BooleanBuilder();

        andBuilder.and(trainingData.homework.num.eq(num));
        andBuilder.and(trainingData.homeworkAnswer.isNotEmpty());


        return (List<TrainingData>) trainingDataRepository.findAll(andBuilder);
    }
}
