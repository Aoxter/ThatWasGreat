package com.github.aoxter.ThatWasGreat.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name="tbl_entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne(cascade=CascadeType.ALL, optional = false)
    private Category category;
    private byte overallRate;
    @ElementCollection
    @CollectionTable(name = "tbl_rates_mapping", joinColumns = {@JoinColumn(name = "entry_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "rate_name")
    @Column(name = "rating")
    private Map<String, Byte> rates;
    //TODO image for entries

    public Entry() {
    }

    public Entry(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Entry(String name, String description, Category category, byte overallRate, Map<String, Byte> rates) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.overallRate = overallRate;
        this.rates = rates;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public byte getOverallRate() {
        return overallRate;
    }

    public void setOverallRate(byte overallRate) {
        this.overallRate = overallRate;
    }

    public Map<String, Byte> getRates() {
        return rates;
    }

    public void setRates(Map<String, Byte> rates) {
        this.rates = rates;
    }
}
