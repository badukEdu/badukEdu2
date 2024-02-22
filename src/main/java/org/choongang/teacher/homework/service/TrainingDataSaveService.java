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
     * @param form - 전송될 숙제 폼
     * @param num - 숙제번호
     */
    public void save(TrainingData trainingData) {
        TrainingData data = null;

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
        trainingDataRepository.save(trainingData);
    }
}
