package org.choongang.teacher.homework.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.teacher.homework.entities.QTrainingData;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkDeleteService {
    private final HomeworkRepository homeworkRepository;
    private final TrainingDataRepository trainingDataRepository;
    public void delete(Long num) {
        // homework 연관된 trainingData 있는 지 확인 후 없다면 삭제
        QTrainingData trainingData = QTrainingData.trainingData;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(trainingData.homework.num.eq(num));

        List<TrainingData> trainingDataList = (List<TrainingData>) trainingDataRepository.findAll(andBuilder);
        if (!trainingDataList.isEmpty()) {
            throw new AlertBackException("해당 숙제로 배포된 정보가 있어 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        homeworkRepository.deleteById(num);
        homeworkRepository.flush();
    }

}
