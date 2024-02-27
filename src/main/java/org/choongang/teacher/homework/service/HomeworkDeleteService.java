package org.choongang.teacher.homework.service;

import lombok.RequiredArgsConstructor;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeworkDeleteService {
    private final HomeworkRepository homeworkRepository;

    public void delete(Long num) {
        // trainingData 있는 지 확인 후 없다면 삭제
        
        homeworkRepository.deleteById(num);
    }

}
