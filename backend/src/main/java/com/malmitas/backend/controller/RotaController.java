package com.malmitas.backend.controller;

import com.malmitas.backend.model.Order;
import com.malmitas.backend.model.Route;
import com.malmitas.backend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("routes")
public class RotaController {

    @Autowired
    private RouteService otimizador;

    @PostMapping("/otimizar")
    public List<Route> otimizarRotas(@RequestBody List<Order> pedidos) {

        return otimizador.gerarRotasOtimizadas(pedidos);
    }

    @GetMapping("/link-rotas/{id}")
    public String generateGoogleMapsUrl(@PathVariable Long id) {
        String url = otimizador.generateGoogleMapsUrl(id);
        return url;
    }

    @GetMapping("/entregadores")
    public int numeroDeEntregadoresParaRealizar(){
        return otimizador.calcularEntregadoresNecessariosBaseadoNoTempo();
    }

    @GetMapping
    public List<Route> findAll() {
        return otimizador.findAll();
    }

    @PutMapping("/concluir-rota")
    public Route concluirRota(@RequestBody Route route) {
        return otimizador.concluirRota(route);
    }

    @DeleteMapping("/{id}")
    public void deletarRota(@PathVariable Long id) {
        otimizador.deletarRota(id);
    }
}
