package org.choongang.board.repositories;

import org.choongang.board.entities.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}
