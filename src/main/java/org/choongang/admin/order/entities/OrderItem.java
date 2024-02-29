package org.choongang.admin.order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.commons.entities.Base;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderItem extends Base {
    @Id
    @GeneratedValue
    private Long num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orderNo")
    private OrderInfo orderInfo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="gameContentNum")
    private GameContent gameContent;

    private long subscriptionMonths; // 구독개월수

    @Column(length=100)
    private String gameTitle; // 게임명
    private long salePrice; // 게임 판매가
    private LocalDate startDate; // 구독 시작일
    private LocalDate endDate; // 구독 종료일
    private String orderName; // 구매자명
    private long totalPayment; // 주문합계

}
