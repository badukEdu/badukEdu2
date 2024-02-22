package org.choongang.admin.education.repositories;

import org.choongang.admin.education.entities.EduData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EduDataRepository extends JpaRepository<EduData, Long>, QuerydslPredicateExecutor<EduData> {
}
