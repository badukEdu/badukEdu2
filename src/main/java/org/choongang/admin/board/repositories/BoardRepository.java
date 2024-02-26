package org.choongang.admin.board.repositories;

import org.choongang.admin.board.entities.Notice_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BoardRepository extends JpaRepository<Notice_, Long>, QuerydslPredicateExecutor<Notice_> {
    List<Notice_> findByOrderByOnTopDesc();

}
