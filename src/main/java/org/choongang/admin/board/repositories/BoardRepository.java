package org.choongang.admin.board.repositories;

import org.choongang.admin.board.entities.Notice_;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Notice_, Long> {
    List<Notice_> findByOrderByOnTopDesc();
}
