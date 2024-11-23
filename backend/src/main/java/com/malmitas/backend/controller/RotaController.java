package com.malmitas.backend.controller;

import com.malmitas.backend.model.Order;
import com.malmitas.backend.model.Route;
import com.malmitas.backend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("routes")
public class RotaController {

    @Autowired
    private RouteService otimizador;

    @PostMapping("/otimizar")
    public List<Route> otimizarRotas(@RequestBody List<Order> pedidos) {
        List<Route> rotas = otimizador.agruparPedidos(pedidos, 12);
        for (Route rota : rotas) {
            Order pontoInicial = new Order(); // Coordenadas da cozinha
            pontoInicial.setLatitude(-16.39925);
            pontoInicial.setLongitude(-49.22721);
            List<Order> rotaOtimizada = otimizador.otimizarRota(pontoInicial, rota.getOrders());
            rota.setOrders(rotaOtimizada);
        }
        return rotas;
    }
}
