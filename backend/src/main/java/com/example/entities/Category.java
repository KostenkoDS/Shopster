package com.example.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("CATEGORIES")
public class Category {
    @Id
    Long id;
    Type name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getName() {
        return name;
    }

    public void setName(Type name) {
        this.name = name;
    }

    public enum Type {
        GRAPHICS_CARD, CPU, RAM, MOTHERBOARD, POWER_SUPPLY;
    }
}