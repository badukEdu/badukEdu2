package org.choongang.teacher.homework.repositories;

import org.choongang.teacher.homework.entities.TrainingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TrainingDataRepository extends JpaRepository<TrainingData, Long>, QuerydslPredicateExecutor<TrainingData> {
}
