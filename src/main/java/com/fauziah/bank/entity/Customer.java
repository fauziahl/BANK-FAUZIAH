package com.fauziah.bank.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Setter
@Getter
public class Customer {

    @Id
    @Column(name = "number_id", length = 16)
    private String numberId;

    @Column(name = "fullname", nullable = false, length = 100)
    private String fullname;

    @Column(name = "address")
    private String address;

    @Column(name = "place_of_birth", nullable = false, length = 20)
    private String placeOfBirth;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "phone_number", nullable = false, length = 13)
    private String phoneNumber;

    @Column(name ="created_date" , nullable = false)
    private LocalDate createdDate;

    @Column(name = "create_by", nullable = false, length = 20)
    private String createdBy="1";

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "modified_by", length = 20)
    private String modifiedBy;

}
