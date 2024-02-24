package org.choongang.education.homework.service;

import lombok.RequiredArgsConstructor;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.entities.QHomework;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EduHomeworkInfoService {

    private final HomeworkRepository homeworkRepository;

    public List<Homework> getlist() {
        // trainingdata -> homework
        QHomework homework = QHomework.homework;

        return null;
    }

}
