package com.malmitas.backend.model.dtos.response;

import com.malmitas.backend.model.Order;

import java.util.List;

public class RouteResponse {
    private List<Order> orders;
    private double distance;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
