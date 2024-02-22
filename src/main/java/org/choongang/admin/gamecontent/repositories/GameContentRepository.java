package org.choongang.admin.gamecontent.repositories;

import org.choongang.admin.gamecontent.entities.GameContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GameContentRepository extends JpaRepository<GameContent, Long>, QuerydslPredicateExecutor<GameContent> {
}
