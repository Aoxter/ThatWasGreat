package com.github.aoxter.ThatWasGreat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_game")
public class Game extends Category {
    private byte contentAmountRate;
    private byte gameplayRate;
    private byte graphicRate;
    private byte plotRate;
    private byte soundtrackRate;

    public Game(String name, String description, byte overallRate, byte contentAmountRate, byte gameplayRate, byte graphicRate, byte plotRate, byte soundtrackRate) {
        super(name, description, overallRate);
        this.contentAmountRate = contentAmountRate;
        this.gameplayRate = gameplayRate;
        this.graphicRate = graphicRate;
        this.plotRate = plotRate;
        this.soundtrackRate = soundtrackRate;
    }

    public byte getContentAmountRate() {
        return contentAmountRate;
    }

    public void setContentAmountRate(byte contentAmountRate) {
        this.contentAmountRate = contentAmountRate;
    }

    public byte getGameplayRate() {
        return gameplayRate;
    }

    public void setGameplayRate(byte gameplayRate) {
        this.gameplayRate = gameplayRate;
    }

    public byte getGraphicRate() {
        return graphicRate;
    }

    public void setGraphicRate(byte graphicRate) {
        this.graphicRate = graphicRate;
    }

    public byte getPlotRate() {
        return plotRate;
    }

    public void setPlotRate(byte plotRate) {
        this.plotRate = plotRate;
    }

    public byte getSoundtrackRate() {
        return soundtrackRate;
    }

    public void setSoundtrackRate(byte soundtrackRate) {
        this.soundtrackRate = soundtrackRate;
    }
}
