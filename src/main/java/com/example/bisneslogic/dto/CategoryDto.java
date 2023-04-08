package com.example.bisneslogic.dto;

import jakarta.validation.constraints.NotEmpty;


public class CategoryDto {

    @NotEmpty
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
