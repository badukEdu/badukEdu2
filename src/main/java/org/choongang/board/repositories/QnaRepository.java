package org.choongang.board.repositories;

import org.choongang.admin.board.entities.Notice_;
import org.choongang.board.entities.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QnaRepository extends JpaRepository<Qna, Long>, QuerydslPredicateExecutor<Qna> {
}
