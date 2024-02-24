package org.choongang.education.homework.service;

import lombok.RequiredArgsConstructor;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingDataInfoService {
    private final TrainingDataRepository trainingDataRepository;

    public List<TrainingData> list() {
        List<TrainingData> trainingDataList = new ArrayList<>();
//        Member member = Member
//        trainingDataRepository.findAllById()


        return trainingDataList;
    }
}
