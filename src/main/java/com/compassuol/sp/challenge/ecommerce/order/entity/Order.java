package com.compassuol.sp.challenge.ecommerce.order.entity;

import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.CONFIRMED;

    @Column(name = "has_discount", nullable = false)
    private boolean hasDiscount = false;

    @Column(name = "total_value", nullable = false)
    private Double totalValue;

    @Column(name = "subtotal_value", nullable = false)
    private Double subTotalValue;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    //nullable until cancelation
    //@CreatedDate
    @Column(name = "cancelation_date")
    private LocalDateTime cancelationDate;

    @Column(name = "cancel_reason")
    private  String cancelReason;
}
