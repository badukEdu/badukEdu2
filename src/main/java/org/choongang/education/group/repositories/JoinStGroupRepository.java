package org.choongang.education.group.repositories;


import org.choongang.education.stgroup.entities.JoinStudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface JoinStGroupRepository extends JpaRepository<JoinStudyGroup, Long>,
        QuerydslPredicateExecutor<JoinStudyGroup> {
}
