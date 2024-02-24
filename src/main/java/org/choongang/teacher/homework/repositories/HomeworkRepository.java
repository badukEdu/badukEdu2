package org.choongang.teacher.homework.repositories;

import org.choongang.teacher.homework.entities.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long>, QuerydslPredicateExecutor<Homework> {
    public List<Homework> getByStudyGroupNum(Long num);
}
