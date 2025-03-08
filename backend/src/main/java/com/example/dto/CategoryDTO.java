package com.example.dto;

import com.example.entities.Category;

public class CategoryDTO {
    Long id;
    String name;

    public CategoryDTO(Category c) {
        this.id = c.getId();
        this.name = switch (c.getName()){
            case CPU -> "CPU";
            case GRAPHICS_CARD -> "Graphics cards";
            case RAM -> "RAM";
            case MOTHERBOARD -> "Motherboards";
            case POWER_SUPPLY -> "Power supplies";
            default -> throw new RuntimeException("Unrecognized category name");
        };
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
