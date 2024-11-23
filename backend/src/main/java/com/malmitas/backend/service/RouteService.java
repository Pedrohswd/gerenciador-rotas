package com.malmitas.backend.service;

import com.malmitas.backend.model.Order;
import com.malmitas.backend.model.Route;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    public List<Order> otimizarRota(Order pontoInicial, List<Order> orders) {
        List<Order> rotaOtimizada = new ArrayList<>();
        Order atual = pontoInicial;

        while (!orders.isEmpty()) {
            Order maisProximo = null;
            double menorDistancia = Double.MAX_VALUE;

            for (Order order : orders) {
                double distancia = calcularDistancia(
                        atual.getLatitude(), atual.getLongitude(),
                        order.getLatitude(), order.getLongitude()
                );
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    maisProximo = order;
                }
            }
            rotaOtimizada.add(maisProximo);
            orders.remove(maisProximo);
            atual = maisProximo;
        }

        return rotaOtimizada;
    }

    public static double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public List<Route> agruparPedidos(List<Order> pedidos, int capacidade) {
        List<Route> rotas = new ArrayList<>();
        Route rotaAtual = new Route();
        int marmitasNaRota = 0;

        for (Order pedido : pedidos) {
            if (marmitasNaRota + pedido.getQuantity() <= capacidade) {
                rotaAtual.adicionarPedido(pedido);
                marmitasNaRota += pedido.getQuantity();
            } else {
                rotas.add(rotaAtual);
                rotaAtual = new Route();
                rotaAtual.adicionarPedido(pedido);
                marmitasNaRota = pedido.getQuantity();
            }
        }

        if (!rotaAtual.getOrders().isEmpty()) {
            rotas.add(rotaAtual);
        }

        return rotas;
    }
}
