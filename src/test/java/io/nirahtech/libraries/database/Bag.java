package io.nirahtech.libraries.database;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Bag {
    @Id
    UUID uuid;

    String name;

    public String getName() {
        return name;
    }
    public UUID getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public Bag() {
        
    }
}
