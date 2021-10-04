package com.es.phoneshop.model.order;

import com.es.phoneshop.enums.PaymentMethodType;
import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order extends Cart {
    private Long id;
    private BigDecimal subTotal;
    private BigDecimal deliveryCost;

    public Order() {
    }

    public Order(Cart cart) {
        super(cart);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public PaymentMethodType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentMethodType paymentType) {
        this.paymentType = paymentType;
    }

    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate deliveryDate;
    private String deliveryAddress;
    private PaymentMethodType paymentType;

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(subTotal, order.subTotal) && Objects.equals(deliveryCost, order.deliveryCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subTotal, deliveryCost);
    }
}
