package com.funding.sandbox.model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class StartupProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    @Column(length=1000)
    private String businessIdea;
    private double fundingRequorements;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

}
