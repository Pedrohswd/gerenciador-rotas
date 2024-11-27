package com.malmitas.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ENG_ROLE", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role_id")
    private List<String> roles = new ArrayList<>();

    public void addRole(String role){
        roles.add(role);
    }

    public User(Long id, String username, String password,String name, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.roles.add(role);
    }
}
