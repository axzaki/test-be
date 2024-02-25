package com.obs.testbe.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "T2_ORDER")
@Where(clause = "IS_DELETED = 'N' ")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private int id;
    @Column(name = "ORDER_NO")
    private String orderNo;
    @Column(name = "ITEM_ID")
    private Integer itemId;
    @Column(name = "QTY")
    private Integer quantity;
    @Column(name = "", length = 1)
    private String isDeleted;
    @ManyToOne
    @JoinColumn(name = "ITEM_ID", updatable = false, insertable = false)
    private Item item;

}
