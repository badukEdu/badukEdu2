
package org.choongang.teacher.group.repositories;


import org.choongang.teacher.group.entities.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StGroupRepository extends JpaRepository<StudyGroup, Long>,
        QuerydslPredicateExecutor<StudyGroup> {
}
