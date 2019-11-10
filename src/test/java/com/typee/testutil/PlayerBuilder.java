package com.typee.testutil;

import com.typee.game.Player;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * A utility class to help with building Player objects.
 */
public class PlayerBuilder {

    private static final int DEFAULT_SCORE_VALUE = 3000;
    private static final int DEFAULT_HEALTH_VALUE = 50;
    private static final double DEFAULT_HEALTH_PROPERTY_VALUE = 0.5;
    private int score;
    private int health;
    private SimpleIntegerProperty scoreProperty = new SimpleIntegerProperty(this, "score");
    private SimpleDoubleProperty healthProperty = new SimpleDoubleProperty(this, "health");
    private SimpleBooleanProperty isGameOver = new SimpleBooleanProperty();
    private StringProperty inputTextProperty;

    public PlayerBuilder() {
        score = DEFAULT_SCORE_VALUE;
        scoreProperty.setValue(score);
        health = DEFAULT_HEALTH_VALUE;
        healthProperty.set(DEFAULT_HEALTH_PROPERTY_VALUE);
        isGameOver.set(false);
    }

    /**
     * Initializes the PlayerBuilder with the data of {@code personToCopy}.
     */
    public PlayerBuilder(Player playerToCopy) {
        score = playerToCopy.getScore();
        scoreProperty.setValue(score);
        health = playerToCopy.getHealth();
        healthProperty.set(health);
        isGameOver.set(false);
    }

    /**
     * Sets the {@code score} of the {@code Player} that we are building.
     */
    public PlayerBuilder withScore(int score) {
        this.score = score;
        return this;
    }

    /**
     * Sets the {@code health} of the {@code Player} that we are building.
     */
    public PlayerBuilder withHealth(int health) {
        this.health = health;
        if (health == 0) {
            isGameOver.set(true);
        }
        return this;
    }

    public Player build() {
        return new Player(score, health, scoreProperty, healthProperty, isGameOver, inputTextProperty);
    }
}
