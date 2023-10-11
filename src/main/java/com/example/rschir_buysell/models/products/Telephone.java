package com.example.rschir_buysell.models.products;

import com.example.rschir_buysell.models.Product;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Telephone extends Product {
    @Column
    private String manufacturer;

    @Column
    private Integer batteryCapacity;
}
