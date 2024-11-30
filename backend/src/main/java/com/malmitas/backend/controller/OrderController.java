package com.malmitas.backend.controller;

import com.malmitas.backend.model.Order;
import com.malmitas.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@Tag(name = "Order Management", description = "Endpoints para gerenciar pedidos.")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Criar um novo pedido", description = "Salva um pedido no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Order> save(@RequestBody Order order) throws Exception {
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping()
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista de todos os pedidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping(value = "/{username}")
    @Operation(summary = "Listar pedidos por usuário", description = "Retorna todos os pedidos associados a um usuário específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos do usuário retornados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<Order>> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(orderService.findAllByUser(username));
    }

    @DeleteMapping()
    @Operation(summary = "Deletar um pedido", description = "Remove um pedido com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public void delete(@RequestBody Order order) {
        orderService.deletById(order);
    }
}
