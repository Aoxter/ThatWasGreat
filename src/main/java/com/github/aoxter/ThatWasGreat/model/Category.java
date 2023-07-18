package com.github.aoxter.ThatWasGreat.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tbl_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(unique=true, nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RatingForm ratingForm;
    @ElementCollection
    @CollectionTable(name="tbl_category_factors", joinColumns=@JoinColumn(name="category_id"))
    @Column(name = "factor")
    private List<String> factors;
    //TODO icon for category


    public Category() {
    }

    public Category(String name, RatingForm ratingForm) {
        this.name = name;
        this.ratingForm = ratingForm;
    }

    public Category(String name, String description, RatingForm ratingForm, List<String> factors) {
        this.name = name;
        this.description = description;
        this.ratingForm = ratingForm;
        this.factors = factors;
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
