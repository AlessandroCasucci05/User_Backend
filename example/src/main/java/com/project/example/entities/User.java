package com.project.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name="users")
@Data
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="address")
    private String address;
}
