package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
public class Program {

    @Id
    private String id;

    private String name;

    private String originalName;

    @Column(name = "category1_name")
    private String category1Name;

    @Column(name = "category2_name")
    private String category2Name;

    private String director;

    private String cast;

    private int grade;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String production;

    private int productYear;

    private String productCountry;

}
