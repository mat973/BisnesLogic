package com.example.bisneslogic.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class PersonDto {


    @NotEmpty
    @Size(min = 2, max = 30, message = "Name should be greater then 2 and lower then 30")
    private String name;


    @Email
    @NotEmpty
    private String email;


    @Min(value = 0, message = "ege should be grater then 0")
    private Integer age;
}
