package org.choongang.admin.board.repositories;

import org.choongang.admin.board.entities.NoticeComment;
import org.choongang.admin.board.entities.Reply_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReplyRepository extends JpaRepository<Reply_, Long>, QuerydslPredicateExecutor<Reply_> {
}
