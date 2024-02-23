package org.choongang.teacher.homework.service;

import lombok.RequiredArgsConstructor;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeworkDeleteService {
    private final HomeworkRepository homeworkRepository;

    public void delete(Long num) {
        homeworkRepository.deleteById(num);
    }

}
