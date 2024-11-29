package com.malmitas.backend.model.dtos.response;

import com.malmitas.backend.model.Order;

import java.util.Date;
import java.util.List;

public class RouteResponse {
    private List<Order> orders;
    private double distance;
    private double duration;
    private int numerosEntregadores;
    private Date dataHora;

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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getNumerosEntregadores() {
        return numerosEntregadores;
    }

    public void setNumerosEntregadores(int numerosEntregadores) {
        this.numerosEntregadores = numerosEntregadores;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}
