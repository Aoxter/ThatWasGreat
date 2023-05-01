package com.github.aoxter.ThatWasGreat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_movie")
public class Movie extends Category {
    private byte actorsRate;
    private byte effectsRate;
    private byte musicRate;
    private byte plotRate;

    public Movie(String name, String description, byte overallRate, byte actorsRate, byte effectsRate, byte musicRate, byte plotRate) {
        super(name, description, overallRate);
        this.actorsRate = actorsRate;
        this.effectsRate = effectsRate;
        this.musicRate = musicRate;
        this.plotRate = plotRate;
    }

    public byte getActorsRate() {
        return actorsRate;
    }

    public void setActorsRate(byte actorsRate) {
        this.actorsRate = actorsRate;
    }

    public byte getEffectsRate() {
        return effectsRate;
    }

    public void setEffectsRate(byte effectsRate) {
        this.effectsRate = effectsRate;
    }

    public byte getMusicRate() {
        return musicRate;
    }

    public void setMusicRate(byte musicRate) {
        this.musicRate = musicRate;
    }

    public byte getPlotRate() {
        return plotRate;
    }

    public void setPlotRate(byte plotRate) {
        this.plotRate = plotRate;
    }
}
