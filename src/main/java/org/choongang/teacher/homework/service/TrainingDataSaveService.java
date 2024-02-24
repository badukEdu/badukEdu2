package org.choongang.teacher.homework.service;

import lombok.RequiredArgsConstructor;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingDataSaveService {

    private final HomeworkInfoService homeworkInfoService;
    private final TrainingDataRepository trainingDataRepository;

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
}
