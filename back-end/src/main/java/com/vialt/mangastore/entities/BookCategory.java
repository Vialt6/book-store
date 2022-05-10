package com.vialt.mangastore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="tbl_category")
@Setter
@Getter
@ToString
public class BookCategory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="category_name")
    private String categoryName;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="category")
    @JsonIgnore
    private Set<Book> book;

}

