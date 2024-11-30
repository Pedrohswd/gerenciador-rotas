package com.malmitas.backend.controller;

import com.malmitas.backend.model.Order;
import com.malmitas.backend.model.Route;
import com.malmitas.backend.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("routes")
@Tag(name = "Route Management", description = "Endpoints for managing and optimizing delivery routes")
public class RotaController {

    @Autowired
    private RouteService otimizador;
    @Operation(summary = "Optimize routes", description = "Generates optimized delivery routes based on the given orders.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Routes optimized successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Route.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/otimizar")
    public List<Route> otimizarRotas(@RequestBody List<Order> pedidos) {
        return otimizador.gerarRotasOtimizadas(pedidos);
    }

    @Operation(summary = "Generate Google Maps URL", description = "Creates a Google Maps URL for a specific route by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Google Maps URL generated successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Route not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/link-rotas/{id}")
    public String generateGoogleMapsUrl(@PathVariable Long id) {
        return otimizador.generateGoogleMapsUrl(id);
    }

    @Operation(summary = "Calculate required delivery personnel", description = "Calculates the number of delivery personnel needed based on the delivery time constraints.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Number of delivery personnel calculated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/entregadores")
    public int numeroDeEntregadoresParaRealizar() {
        return otimizador.calcularEntregadoresNecessariosBaseadoNoTempo();
    }

    @Operation(summary = "Get all routes", description = "Retrieves a list of all available routes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Routes retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Route.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public List<Route> findAll() {
        return otimizador.findAll();
    }

    @Operation(summary = "Complete a route", description = "Marks a specific route as completed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route marked as completed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Route.class))),
            @ApiResponse(responseCode = "404", description = "Route not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/concluir-rota")
    public Route concluirRota(@RequestBody Route route) {
        return otimizador.concluirRota(route);
    }

    @Operation(summary = "Delete a route", description = "Deletes a specific route by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Route not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deletarRota(@PathVariable Long id) {
        otimizador.deletarRota(id);
    }
}
