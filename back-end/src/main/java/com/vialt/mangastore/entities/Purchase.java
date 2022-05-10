package com.vialt.mangastore.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Basic
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "purchase_time")
    private Date purchaseTime;

    @Column(name = "user")
    private String buyer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    @JsonIgnore
    //@JsonBackReference
    private List<ProductInPurchase> productsInPurchase;




}
