package org.choongang.admin.order.repositories;

import org.choongang.admin.order.entities.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long>, QuerydslPredicateExecutor<OrderInfo> {

}
