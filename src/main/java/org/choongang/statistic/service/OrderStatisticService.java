package org.choongang.statistic.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.order.entities.QOrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatisticService {
  private final EntityManager em;

  public List<Object[]> getData(LocalDate sDate, LocalDate eDate, String type) {
    type = StringUtils.hasText(type) ? type : "DAY";

    QOrderInfo orderInfo = QOrderInfo.orderInfo;
    BooleanBuilder andBuilder = new BooleanBuilder();

    andBuilder.and(orderInfo.createdAt.between(LocalDateTime.of(sDate, LocalTime.of(0, 0, 0)), LocalDateTime.of(eDate, LocalTime.of(23, 59, 59))));
    int length = type.equals("MONTH") ? 7 : 10;
    List<Tuple> items =
        new JPAQueryFactory(em).from(orderInfo)
            .select(Expressions.asString(orderInfo.createdAt.stringValue().substring(0, length + 1)), orderInfo.totalPayment.sum())
            .groupBy(Expressions.asString(orderInfo.createdAt.stringValue().substring(0, length + 1)))
            .fetch();

    List<Object[]> data = items.stream().map(item -> new Object[] {item.get(0, String.class), item.get(1, int.class)}).toList();

    return data;
  }
}
