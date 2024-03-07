package com.example.bisneslogic.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationDto {
    @NotEmpty
    @Size(min = 2, max = 30, message = "Name should be greater then 2 and lower then 30")
    private String name;


    private String password;
}
