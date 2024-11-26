package com.example.bookmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "users")
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    private String email;
    private String name;
    private String password;
}
