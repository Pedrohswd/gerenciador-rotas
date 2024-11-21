package com.malmitas.backend.controller;

import com.malmitas.backend.model.Order;
import com.malmitas.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class  OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Order> save(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping()
    public ResponseEntity<List<Order>> save() {
        return ResponseEntity.ok(orderService.getAll());
    }

}
