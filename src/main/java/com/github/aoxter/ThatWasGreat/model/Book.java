package com.github.aoxter.ThatWasGreat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_book")
public class Book extends Category {
    private byte charactersRate;
    private byte plotRate;
    private byte worldRate;

    public Book(String name, String description, byte overallRate, byte charactersRate, byte plotRate, byte worldRate) {
        super(name, description, overallRate);
        this.charactersRate = charactersRate;
        this.plotRate = plotRate;
        this.worldRate = worldRate;
    }

    public byte getCharactersRate() {
        return charactersRate;
    }

    public void setCharactersRate(byte charactersRate) {
        this.charactersRate = charactersRate;
    }

    public byte getPlotRate() {
        return plotRate;
    }

    public void setPlotRate(byte plotRate) {
        this.plotRate = plotRate;
    }

    public byte getWorldRate() {
        return worldRate;
    }

    public void setWorldRate(byte worldRate) {
        this.worldRate = worldRate;
    }
}
