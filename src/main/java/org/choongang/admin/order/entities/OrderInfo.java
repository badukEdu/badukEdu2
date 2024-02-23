package org.choongang.admin.order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.admin.order.constants.PaymentMethod;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;

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
    private String orderName;

    @Column(length=40, nullable = false)
    private String orderMobile;

    @Enumerated(EnumType.STRING)
    @Column(length=30, nullable = false)
    private PaymentMethod paymentMethod = PaymentMethod.BANK_TRANSFER;

    private long totalPayment;

}
