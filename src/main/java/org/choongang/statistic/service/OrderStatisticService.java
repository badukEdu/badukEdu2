package org.choongang.statistic.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.order.entities.QOrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStatisticService {
  private final JPAQueryFactory jpaQueryFactory;

  public Map<String, List<Long>> getData(LocalDate sDate, String type) {
    return getData(sDate, null, type);
  }

  public Map<String, List<Long>> getData(LocalDate sDate, LocalDate eDate, String type) {
    type = StringUtils.hasText(type) ? type : "DAY";

    eDate = Objects.requireNonNullElse(eDate, LocalDate.now());

    QOrderInfo orderInfo = QOrderInfo.orderInfo;
    BooleanBuilder andBuilder = new BooleanBuilder();

    andBuilder.and(orderInfo.createdAt.between(LocalDateTime.of(sDate, LocalTime.of(0, 0, 0)), LocalDateTime.of(eDate, LocalTime.of(23, 59, 59))));
    int length = type.equals("MONTH") ? 7 : 10;
    List<Tuple> items =
            jpaQueryFactory.from(orderInfo)
            .select(Expressions.asString(orderInfo.createdAt.stringValue().substring(0, length)), orderInfo.totalPayment.sum(), orderInfo.count())
            .groupBy(Expressions.asString(orderInfo.createdAt.stringValue().substring(0, length)))
            .fetch();

    Map<String, List<Long>> data = items.stream().collect(Collectors.toMap(item -> item.get(0, String.class), item -> Arrays.asList(item.get(1, Long.class), item.get(2, Long.class))));

    return data;
  }
}
