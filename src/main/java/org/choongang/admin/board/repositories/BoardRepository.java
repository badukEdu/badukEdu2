package org.choongang.admin.board.repositories;

import org.choongang.admin.board.entities.Notice_;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Notice_, Long> {
}
