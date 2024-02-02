package com.compassuol.sp.challenge.ecommerce.order.entity;

import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.CANCELED;
import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.CONFIRMED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    public static Double DISCOUNT_PERCENTAGE = 0.05;
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

    @Column(name = "total_value")
    private Double totalValue;

    @Column(name = "subtotal_value")
    private Double subTotalValue = 0.0;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();


    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    //@CreatedDate
    @Column(name = "cancelation_date")
    private LocalDateTime cancelationDate;

    @Column(name = "cancel_reason")
    private  String cancelReason;


    public void addProduct(Product product, int quantity){
        for (int i =0 ; i<quantity; i++)
            this.getProducts().add(product);
    }
    @PrePersist
    @PreUpdate
    public void processValue(){
        this.subTotalValue = 0.0;
        this.products.forEach(product -> this.subTotalValue+=product.getPrice());
        this.totalValue = this.subTotalValue;
        if (this.paymentMethod==PaymentMethod.PIX)
            this.totalValue -= this.totalValue * DISCOUNT_PERCENTAGE;
    }

    public void cancel(String cancelReason){
        LocalDateTime cancelationPeriod = this.getCreationDate().plusDays(90);
        if(this.orderStatus == OrderStatus.CONFIRMED && LocalDateTime.now().isBefore(cancelationPeriod)){
            this.orderStatus = CANCELED;
            this.cancelationDate = LocalDateTime.now();
            this.cancelReason = cancelReason;
        }else if (this.orderStatus != CONFIRMED){
            throw new OrderStatusNotAuthorizedException(
                    "O status do pedido deve ser diferente de SENT"
            );
        }else if (LocalDateTime.now().isAfter(cancelationPeriod)){
            throw new OrderStatusNotAuthorizedException(
                    "O cancelamento do pedido só pode ser feito antes de 90 dias da compra"
            );
        }
    }



}
