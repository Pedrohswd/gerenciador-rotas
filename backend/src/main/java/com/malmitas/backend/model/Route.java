package com.malmitas.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Route {
    private List<Order> orders;
    private double distanciaTotal;

    public Route() {
        this.orders = new ArrayList<>();
        this.distanciaTotal = 0.0;
    }

    public void adicionarPedido(Order order) {
        this.orders.add(order);
    }

}
