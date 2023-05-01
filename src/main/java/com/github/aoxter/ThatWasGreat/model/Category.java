package com.github.aoxter.ThatWasGreat.model;

import jakarta.persistence.*;

@Entity
@Table(name="tbl_category")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;
    //TODO for category or entry?
    @Column(nullable = false)
    private String name;
    private String description;
    private byte overallRate;
    //TODO image for entries
    //TODO static icon for category

    //TODO connection between categories and entries

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(String name, byte overallRate) {
        this.name = name;
        this.description = description;
        this.overallRate = overallRate;
    }

    public Category(String name, String description, byte overallRate) {
        this.name = name;
        this.description = description;
        this.overallRate = overallRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getOverallRate() {
        return overallRate;
    }

    public void setOverallRate(byte overallRate) {
        this.overallRate = overallRate;
    }
}
