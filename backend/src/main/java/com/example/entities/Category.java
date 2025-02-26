package com.example.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("CATEGORIES")
public class Category {
    @Id
    Long id;
    Type name;

    public enum Type {
        GRAPHICS_CARD, CPU, RAM, MOTHERBOARD, POWER_SUPPLY;
    }
}