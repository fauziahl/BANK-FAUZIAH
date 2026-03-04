package com.fauziah.bank.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDTO {

    @NotBlank(message = "Number ID cannot be empty")
    @Size(min = 16, max = 16, message = "Number ID must be 16 characters")
    @JsonProperty("numberId")
    private String numberId;

    @NotBlank(message = "Fullname cannot be empty")
    private String fullname;
    private String address;
    private String placeOfBirth;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private String phoneNumber;
}
