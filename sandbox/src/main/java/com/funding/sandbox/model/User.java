package com.funding.sandbox.model;
import jakarta.persistence.*;
import lombok.Data;
import com.funding.sandbox.model.UserRole;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
