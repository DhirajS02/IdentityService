package com.example.identityservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    public Role(@NotNull String name) {
        this.name = ERole.valueOf(name.toUpperCase());
    }
}
