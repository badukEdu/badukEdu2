package org.choongang.education.homework.service;

import lombok.RequiredArgsConstructor;
import org.choongang.teacher.homework.controllers.RequestTrainingData;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EduTrainingDataSaveService {

    private final TrainingDataInfoService trainingDataInfoService;
    private final TrainingDataRepository trainingDataRepository;


    public void save(RequestTrainingData form) {
        TrainingData trainingData = (TrainingData) trainingDataRepository.findById(form.getNum()).orElseThrow();
        trainingData.setHomeworkAnswer(form.getHomeworkAnswer());
        trainingData.setQuestion(form.getQuestion());

        trainingDataRepository.saveAndFlush(trainingData);
    }
}
