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
    @Column(name = "number_id")
    private String numberId;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "address")
    private String address;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name ="created_date" , nullable = false)
    private LocalDate createdDate;

    @Column(name = "create_by", nullable = false)
    private Integer createdBy=1;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "modified_by")
    private Integer modifiedBy;

}
