package com.malmitas.backend.service;

import com.malmitas.backend.model.dtos.DistanceDuration;
import com.malmitas.backend.model.dtos.response.DistanceMatrixResponse;
import com.malmitas.backend.model.Order;
import com.malmitas.backend.model.Route;
import com.malmitas.backend.model.dtos.response.GeoLocationResponse;
import com.malmitas.backend.model.dtos.response.RouteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private GoogleLocationService googleLocationService;
    private static final String API_KEY = "AIzaSyAD1GxRYSjkAJk_brXwpIkJBRouKgzUtAA";
    private static final int JANELA_TEMPO_SEGUNDOS = 4 * 3600;

    public RouteResponse otimizarRota(Order pontoInicial, List<Order> orders) {
        List<Order> rotaOtimizada = new ArrayList<>();
        Order atual = pontoInicial;
        double distanciaTrecho = 0;
        double tempoTrecho = 0;
        int entregadoresNecessarios = 0;
        RouteResponse response = new RouteResponse();

        while (!orders.isEmpty()) {
            Order maisProximo = null;
            double menorDistancia = Double.MAX_VALUE;

            for (Order order : orders) {
                DistanceDuration distancia = calcularDistanciaViaAPI(
                        atual.getLatitude(), atual.getLongitude(),
                        order.getLatitude(), order.getLongitude()
                );
                if (distancia.getDistance() < menorDistancia) {
                    menorDistancia = distancia.getDistance();
                    distanciaTrecho = distancia.getDistance();
                    tempoTrecho = distancia.getDuration();
                    maisProximo = order;
                }
            }
            rotaOtimizada.add(maisProximo);
            response.setDistance(distanciaTrecho + response.getDistance());
            response.setDuration(tempoTrecho + response.getDuration());
            orders.remove(maisProximo);
            atual = maisProximo;
        }
        response.setOrders(rotaOtimizada);
        
        return response;
    }

    public DistanceDuration calcularDistanciaViaAPI(double latAtual, double lonAtual, double latDest, double lonDest) {

        Mono<DistanceMatrixResponse> locationMono = googleLocationService.getGoogleLocation(
                latAtual,
                lonAtual,
                latDest,
                lonDest,
                API_KEY
        );
        DistanceMatrixResponse locations = locationMono.block();
        double distancia = locations.getRows().get(0).getElements().get(0).getDistance().getValue() / 1000.0; // km
        double duracao = locations.getRows().get(0).getElements().get(0).getDuration().getValue() / 60.0; // minutos

        return new DistanceDuration(distancia, duracao);

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
