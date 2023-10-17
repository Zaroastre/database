package io.nirahtech.libraries.database;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public record Heroe (
    @Id int id,
    String name,

    @Enumerated(EnumType.STRING)
    Race race
) {

}
