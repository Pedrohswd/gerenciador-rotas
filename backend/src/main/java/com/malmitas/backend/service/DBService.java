package com.malmitas.backend.service;

import com.malmitas.backend.model.User;
import com.malmitas.backend.repository.RouteRepository;
import com.malmitas.backend.repository.UserRepository;
import com.malmitas.backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
public class DBService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Transactional
    public void instanciaDB() {
        User user = new User(null, "admin", Utils.hashPassword("admin"), "ROLE_ADMIN");
        userRepository.save(user);
        //routeRepository.deleteAll();
    }
}
