package com.obs.testbe.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Table(name = "T1_ITEM")
@Where(clause = "IS_DELETED = 'N' ")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "IS_DELETED", length = 1)
    private String isDeleted;
}
