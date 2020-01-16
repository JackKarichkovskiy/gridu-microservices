package com.gridu.microservices.catalogapp.catalog.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {

    @Id
    @Column(name = "UNIQ_ID")
    private String id;

    @Column(name = "SKU")
    private String sku;
}
