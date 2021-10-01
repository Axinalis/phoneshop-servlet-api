package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.util.Objects;

public class Order extends Cart {
    private BigDecimal subTotal;
    private BigDecimal deliveryCost;

    public Order() {
    }

    public Order(Cart cart) {
        super(cart);
    }

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
