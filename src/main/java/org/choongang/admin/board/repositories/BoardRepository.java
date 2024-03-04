package org.choongang.admin.board.repositories;

import org.choongang.admin.board.entities.Notice_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;

public interface BoardRepository extends JpaRepository<Notice_, Long>, QuerydslPredicateExecutor<Notice_> {
    List<Notice_> findByOrderByOnTopDesc();

    List<Notice_> findTop5ByScheduledDateIsNullAndTypeOrderByCreatedAtDesc(String type);

    List<Notice_> findByPostingTypeAndScheduledDateBefore(String type, LocalDate scheduledDate);
}
