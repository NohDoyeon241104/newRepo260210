package com.ubisam.example.worlds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class World {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
}
