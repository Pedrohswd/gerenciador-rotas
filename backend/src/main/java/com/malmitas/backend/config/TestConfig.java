package com.malmitas.backend.config;

import com.malmitas.backend.service.DBService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TestConfig {
    @Autowired
    private DBService dBservice;

    @PostConstruct
    public void instanciaDB(){
        dBservice.instanciaDB();
    }

}
