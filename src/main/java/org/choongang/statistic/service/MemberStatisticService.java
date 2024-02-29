package org.choongang.statistic.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.order.entities.QOrderInfo;
import org.choongang.member.entities.QMember;
import org.choongang.statistic.controllers.RequestSearch;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberStatisticService {

  private final JPAQueryFactory jpaQueryFactory;

  public Map<String, Long> getData(RequestSearch search) {
    return getData(search.getSDate(), search.getEDate(), search.getType());
  }

  public Map<String, Long> getData(LocalDate sDate, String type) {
    return getData(sDate, null, type);
  }

  public Map<String, Long> getData(LocalDate sDate, LocalDate eDate, String type){
    type = StringUtils.hasText(type) ? type.toUpperCase() : "DAY";
    eDate = Objects.requireNonNullElse(eDate, LocalDate.now());

    QMember member = QMember.member;
    BooleanBuilder andBuilder = new BooleanBuilder();
    andBuilder.and(member.createdAt.between(LocalDateTime.of(sDate, LocalTime.of(0,0,0)), LocalDateTime.of(eDate, LocalTime.of(23,59,59))));

    int length = type.equals("MONTH") ? 7 : 10;

    List<Tuple> items = jpaQueryFactory.from(member)
        .select(Expressions.asString(member.createdAt.stringValue().substring(0, length)), member.count())
        .where(andBuilder)
        .groupBy(Expressions.asString(member.createdAt.stringValue().substring(0, length)))
        .fetch();

    Map<String, Long> data = items.stream().collect(Collectors.toMap(item -> item.get(0, String.class), item -> item.get(1, Long.class)));

    return data;
  }

}
