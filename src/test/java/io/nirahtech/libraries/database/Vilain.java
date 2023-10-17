package io.nirahtech.libraries.database;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Vilain {
    
    @Id
    int id;
    String name;

    @Enumerated(EnumType.STRING)
    Race race;

    @OneToOne
    Bag bag;

    public Vilain() {
    }

    public Vilain(int id, String name, Race race) {
        this.id = id;
        this.name = name;
        this.race = race;
    }


    public Bag getBag() {
        return bag;
    }
    public void setBag(Bag bag) {
        this.bag = bag;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
