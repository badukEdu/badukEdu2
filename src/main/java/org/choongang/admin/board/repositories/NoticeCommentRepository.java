package org.choongang.admin.board.repositories;

import org.choongang.admin.board.entities.NoticeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment, Long>, QuerydslPredicateExecutor<NoticeComment> {

}
