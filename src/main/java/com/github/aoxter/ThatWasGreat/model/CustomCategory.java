package com.github.aoxter.ThatWasGreat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Map;

@Entity
@Table(name="tbl_custom_category")
public class CustomCategory extends Category {
    private Map<String, Byte> customRates;

    public CustomCategory(String name, String description, byte overallRate, Map<String, Byte> customRates) {
        super(name, description, overallRate);
        this.customRates = customRates;
    }

    public Map<String, Byte> getCustomRates() {
        return customRates;
    }

    public void setCustomRates(Map<String, Byte> customRates) {
        this.customRates = customRates;
    }
}
