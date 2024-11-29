package com.malmitas.backend.service;

import com.malmitas.backend.model.dtos.DistanceDuration;
import com.malmitas.backend.model.dtos.response.DistanceMatrixResponse;
import com.malmitas.backend.model.Order;
import com.malmitas.backend.model.Route;
import com.malmitas.backend.model.dtos.response.GeoLocationResponse;
import com.malmitas.backend.model.dtos.response.RouteResponse;
import com.malmitas.backend.model.enuns.Status;
import com.malmitas.backend.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private GoogleLocationService googleLocationService;
    @Autowired
    private RouteRepository routeRepository;

    private static final String API_KEY = "AIzaSyAD1GxRYSjkAJk_brXwpIkJBRouKgzUtAA";

    @Autowired
    private OrderService orderService;


    public List<Route> gerarRotasOtimizadas(List<Order> pedidos) {
        if (pedidos.isEmpty()) {
            throw new RuntimeException("Selecione os pedidos");
        }

        List<Route> rotas = agruparPedidos(pedidos, 12);
        for (Route rota : rotas) {
            Order pontoInicial = new Order();
            pontoInicial.setLatitude(-16.39925);
            pontoInicial.setLongitude(-49.22721);
            RouteResponse rotaOtimizada = otimizarRota(pontoInicial, rota.getOrders());
            rota.setOrders(rotaOtimizada.getOrders());
            rota.setDistanciaTotal(rotaOtimizada.getDistance());
            rota.setDuracaoTotal(rotaOtimizada.getDuration());
        }


        return rotas;
    }

    public RouteResponse otimizarRota(Order pontoInicial, List<Order> orders) {
        List<Order> rotaOtimizada = new ArrayList<>();
        Order atual = pontoInicial;
        double distanciaTrecho = 0;
        double tempoTrecho = 0;

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

        Route route = new Route(response);
        alterarStatusCompleted(route);
        routeRepository.save(route);

        return response;
    }

    public void alterarStatusCompleted(Route route){
        for (Order order : route.getOrders()) {
            order.setStatus(Status.COMPLETED);
            orderService.alterarStatus(order);
        }
    }

    public void alterarStatusPending(Route route){
        for (Order order : route.getOrders()) {
            order.setStatus(Status.PENDING);
            orderService.alterarStatus(order);
        }
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

    public int calcularEntregadoresNecessariosBaseadoNoTempo() {
        // Obter rotas do dia atual
        List<Route> rotas = routeRepository.findByCreatedAtAndNotClosed(LocalDate.now());

        // Janela de tempo: das 10h às 14h
        LocalTime inicioJanela = LocalTime.of(14, 0);
        LocalTime fimJanela = LocalTime.of(20, 22);
        LocalTime horaAtual = LocalTime.now();


        double tempoDisponivelHoras = calcularTempoRestanteEmHoras(inicioJanela, fimJanela, horaAtual);

        if (tempoDisponivelHoras <= 0) {
            throw new IllegalStateException("A janela de entrega já expirou!");
        }
        double tempoDasRotasAbertas = 0.0;

        for (Route rota : rotas) {
            tempoDasRotasAbertas += rota.getDuracaoTotal() / 60;
        }
        return (int) Math.ceil(tempoDasRotasAbertas / tempoDisponivelHoras);

    }

    private double calcularTempoRestanteEmHoras(LocalTime inicioJanela, LocalTime fimJanela, LocalTime horaAtual) {
        if (horaAtual.isBefore(inicioJanela)) {
            return (double) (fimJanela.toSecondOfDay() - inicioJanela.toSecondOfDay()) / 3600.0;
        } else if (horaAtual.isAfter(fimJanela)) {
            return 0.0;
        } else {
            return (double) (fimJanela.toSecondOfDay() - horaAtual.toSecondOfDay()) / 3600.0;
        }
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Route concluirRota(Route route) {
        route.setClosed(!route.getClosed());
        return routeRepository.save(route);
    }

    public void deletarRota(Long id) {
        Route rota = routeRepository.findById(id).get();
        alterarStatusPending(rota);
        routeRepository.deleteById(id);
    }

    public String generateGoogleMapsUrl(Long id) {

        Route route = routeRepository.findById(id).orElseThrow();

        if (route.getOrders().isEmpty()) {
            throw new IllegalArgumentException("A rota deve conter ao menos um destino.");
        }

        StringBuilder url = new StringBuilder("https://www.google.com/maps/dir/");

        Order pontoInicial = new Order();
        pontoInicial.setLatitude(-16.39925);
        pontoInicial.setLongitude(-49.22721);

        url.append(pontoInicial.getLatitude())
                .append(",")
                .append(pontoInicial.getLongitude())
                .append("/");

        route.getOrders().forEach(order -> {
            url.append(order.getLatitude())
                    .append(",")
                    .append(order.getLongitude())
                    .append("/");
        });

        String formattedUrl = url.substring(0, url.length() - 1);

        return formattedUrl.trim();
    }
}
