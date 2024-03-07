package com.example.bisneslogic.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigInteger;
import java.time.LocalDate;

public class ScoreDto {


    @JsonView
    @Pattern(regexp = "^\\d{12}$", message = "карта должна состоять из 12 цифр")
    private String digit;

    @JsonView
    @NotBlank
    @Pattern(regexp = "^([\\p{L}\\d\\s\\-\\.,]+)$")
    private String address;
    @NotNull
    private LocalDate deliveryDate;


    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
       this.deliveryDate = deliveryDate;

    }
}
