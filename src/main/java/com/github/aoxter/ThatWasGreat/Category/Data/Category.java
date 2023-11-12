package com.github.aoxter.ThatWasGreat.Category.Data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.aoxter.ThatWasGreat.Entry.Data.Entry;
import com.github.aoxter.ThatWasGreat.Entry.Business.EntryListSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="tbl_category")
public class Category {
    //TODO Configure generator for sequence generator incremented by 1
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    private Long id;
    @NotNull
    @Column(unique=true)
    private String name;
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RatingForm ratingForm;
    @ElementCollection
    @CollectionTable(name="tbl_category_factors", joinColumns=@JoinColumn(name="category_id"))
    @Column(name = "factor")
    private List<String> factors;
    @OneToMany(mappedBy="category", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonSerialize(using = EntryListSerializer.class)
    private List<Entry> entries;
    //TODO icon for category

    public Category() {
    }

    public Category(String name, RatingForm ratingForm) {
        this.name = name;
        this.ratingForm = ratingForm;
    }

    public Category(String name, RatingForm ratingForm, List<String> factors) {
        this.name = name;
        this.ratingForm = ratingForm;
        this.factors = factors;
    }

    public Category(String name, String description, RatingForm ratingForm, List<String> factors) {
        this.name = name;
        this.description = description;
        this.ratingForm = ratingForm;
        this.factors = factors;
    }

    public Long getId() {
        return id;
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

    public RatingForm getRatingForm() {
        return ratingForm;
    }

    public void setRatingForm(RatingForm ratingForm) {
        this.ratingForm = ratingForm;
    }

    public List<String> getFactors() {
        return factors;
    }

    public void setFactors(List<String> factors) {
        this.factors = factors;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        stringBuilder.append(" (Category):\n");
        stringBuilder.append(getDescription());
        stringBuilder.append("\n");
        stringBuilder.append("Rating form: ");
        stringBuilder.append(ratingForm);
        stringBuilder.append("\nRated aspects:");
        String prefix = " ";
        for (String factor: factors){
            stringBuilder.append(prefix);
            stringBuilder.append(factor);
            prefix = ", ";
        }
        return super.toString();
    }
}
