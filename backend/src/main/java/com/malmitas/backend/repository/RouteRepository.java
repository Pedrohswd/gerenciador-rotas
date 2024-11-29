package com.malmitas.backend.repository;

import com.malmitas.backend.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query(value = "SELECT * FROM Route r WHERE r.created_at = :createdAt AND r.status = false", nativeQuery = true)
    List<Route> findByCreatedAtAndNotClosed(@Param("createdAt") LocalDate createdAt);
}
