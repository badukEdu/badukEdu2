package org.choongang.admin.order.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.order.entities.OrderItem;
import org.choongang.admin.order.entities.QOrderItem;
import org.choongang.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, QuerydslPredicateExecutor<OrderItem> {

    default Optional<OrderItem> getMemberItem(Member member, GameContent gameContent) {
        QOrderItem orderItem = QOrderItem.orderItem;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orderItem.orderInfo.member.eq(member))
                .and(orderItem.gameContent.eq(gameContent));

        return findOne(builder);
    }

}
