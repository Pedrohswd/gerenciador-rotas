package com.malmitas.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Route {
    private List<Order> orders =  new ArrayList<>();
    private double distanciaTotal;
    private double duracaoTotal;

    public Route() {
        this.distanciaTotal = 0.0;
    }
    public Route(List<Order> orders, double distance) {
        this.orders = orders;
        this.distanciaTotal = distance;
    }

    public void adicionarPedido(Order order) {
        this.orders.add(order);
    }

}
