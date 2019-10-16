package com.typee.game;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Player of the typing game
 */
public class Player {

    private static final int DEFAULT_SCORE_VALUE = 0;
    private static final int DEFAULT_HEALTH_VALUE = 100;
    private int score;
    private int health;
    private SimpleIntegerProperty scoreProperty = new SimpleIntegerProperty(this, "score");
    private SimpleDoubleProperty healthProperty = new SimpleDoubleProperty(this, "health");
    private SimpleBooleanProperty isGameOver = new SimpleBooleanProperty();
    private StringProperty inputText;

    public Player() {
        score = DEFAULT_SCORE_VALUE;
        scoreProperty.setValue(score);
        health = DEFAULT_HEALTH_VALUE;
        healthProperty.set(health / 100.0);
        isGameOver.set(false);
    }

    public void setInputAs(StringProperty inputText) {
        this.inputText = inputText;
    }

    /**
     * Returns input text by player.
     */
    public String getInputText() {
        return inputText.get().strip();
    }

    /**
     * Increases this player's score by {@code score}.
     */
    public void incrementScore(int score) {
        this.score += score;
        scoreProperty.setValue(score);
    }

    /**
     * Decreases this player's health by {@code health}.
     */
    public void decrementHealth(int health) {
        if (isGameOver.get()) {
            return;
        }
        if (this.health <= 0) {
            isGameOver.set(true);
            this.health = 0;
        }
        this.health -= health;
        healthProperty.set((double) this.health / DEFAULT_HEALTH_VALUE);
    }

    public SimpleIntegerProperty getScoreProperty() {
        return scoreProperty;
    }

    public SimpleDoubleProperty getHealthProperty() {
        return healthProperty;
    }

    public SimpleBooleanProperty getGameOverProperty() {
        return isGameOver;
    }
}
