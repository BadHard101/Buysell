package com.example.rschir_buysell.models.products;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Book extends Product {
    @Column
    private String author;

    @Override
    public String toControllerProductType() {
        return "book";
    }
}
