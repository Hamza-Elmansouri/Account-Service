package com.ecommerce_data_model.demo.account_address;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;

    private String street;
    private String aptBuilding;
    private String city;
    private String stateProvince;
    private String zipCode;
    private String country;

    @ManyToOne
    private Account account;

}
