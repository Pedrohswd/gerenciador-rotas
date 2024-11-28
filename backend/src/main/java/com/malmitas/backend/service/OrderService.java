package com.malmitas.backend.service;

import com.malmitas.backend.model.Order;
import com.malmitas.backend.model.User;
import com.malmitas.backend.model.dtos.response.GeoLocationResponse;
import com.malmitas.backend.model.enuns.Status;
import com.malmitas.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private GeoLocationService geoLocationService;
    @Autowired
    private UserService userService;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) throws Exception {
        Mono<GeoLocationResponse[]> locationMono = geoLocationService.getGeoLocation(
                order.getStreet(),
                order.getCounty(),
                order.getCity(),
                order.getState()
        );
        GeoLocationResponse[] locations = locationMono.block();
        if (locations != null && locations.length > 0) {
            order.setLatitude(Double.parseDouble(locations[0].getLatitude()));
            order.setLongitude(Double.parseDouble(locations[0].getLongitude()));
        }else {
            throw new Exception("Endereço não encontrado! Verifique.");
        }
        User user = userService.findByUsername(order.getCreatedBy().getUsername());
        order.setCreatedBy(user);
        order.setStatus(Status.PENDING);
        return orderRepository.save(order);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public void deletById(Order order) {
        orderRepository.delete(order);
    }

    public void deleteAllById(List<Order> orders) {
        orderRepository.deleteAll(orders);
    }

    public List<Order> findAllByUser(String username) {
        User user = userService.findByUsername(username);
        return orderRepository.findByCreatedBy(user);
    }
}
