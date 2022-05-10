package com.vialt.mangastore.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "product_in_purchase")
public class ProductInPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "related_purchase")
    @JsonIgnore
    @ToString.Exclude
    private Purchase purchase;

    @Basic
    @Column(name = "name", nullable = true)
    private String name;

    @Basic
    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Basic
    @Column(name = "unit_price", nullable = true)
    private BigDecimal unitPrice;

    @Basic
    @Column(name = "quantity", nullable = true)
    private int quantity;

    /*
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book")
    //@JsonIgnore
    private Book book;


     */

    @Basic
    @Column(name = "book", nullable = true)
    private Long bookId;
}
