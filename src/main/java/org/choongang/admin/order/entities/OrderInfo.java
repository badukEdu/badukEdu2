package org.choongang.admin.order.entities;

import jakarta.persistence.*;
import lombok.*;
import org.choongang.admin.order.constants.PaymentMethod;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo extends Base {
    @Id
    private Long orderNo;

    @ManyToOne
    @JoinColumn(name="memberNum")
    private Member member;

    @Column(length=40, nullable = false)
    private String orderName; // 구매자명

    @Column(length=40, nullable = false)
    private String orderMobile; // 연락처

    @Enumerated(EnumType.STRING)
    @Column(length=30, nullable = false)
    private PaymentMethod paymentMethod = PaymentMethod.BANK_TRANSFER; // 결제방법선택, 기본은 무통장입금 

    private long totalPayment; // 주문합계

    @ToString.Exclude
    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

}
