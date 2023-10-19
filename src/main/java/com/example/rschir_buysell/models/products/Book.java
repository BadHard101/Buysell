package com.example.rschir_buysell.models.products;

import com.example.rschir_buysell.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Book extends Product {
    @Column
    private String author;
}
