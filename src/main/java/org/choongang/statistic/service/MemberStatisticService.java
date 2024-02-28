package org.choongang.statistic.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
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

@Service
@RequiredArgsConstructor
public class MemberStatisticService {

//  private final EntityManager em;
//
//  public void getData(LocalDate sDate, LocalDate eDate, String type) {
//    type = StringUtils.hasText(type) ? type : "DAY";
//
//    QOrderInfo orderInfo = QOrderInfo.orderInfo;
//    BooleanBuilder andBuilder = new BooleanBuilder();
//
//    andBuilder.and(orderInfo.createdAt.between(LocalDateTime.of(sDate, LocalTime.of(0,0,0))
//        , LocalDateTime.of(eDate, LocalTime.of(23,59,59))));
//
//    new JPAQueryFactory(em).from(orderInfo)
//        .select(orderInfo.totalPayment.sum())
//        .groupBy(Expressions.asString(orderInfo.createdAt.stringValue()))
//        .fetch();
//
//  }
}
