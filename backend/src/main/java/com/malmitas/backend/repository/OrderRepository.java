package com.malmitas.backend.repository;

import com.malmitas.backend.model.Order;
import com.malmitas.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedBy(User createdBy);
}
