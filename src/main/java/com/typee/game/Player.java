package com.typee.game;

import static com.typee.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Player of the typing game
 */
public class Player {

    private static final Logger logger = LogsCenter.getLogger(Player.class);
    private static final int DEFAULT_SCORE_VALUE = 0;
    private static final int DEFAULT_HEALTH_VALUE = 100;
    private static final int DEFAULT_HEALTH_PROPERTY_VALUE = 1;
    private int score;
    private int health;
    private SimpleIntegerProperty scoreProperty = new SimpleIntegerProperty(this, "score");
    private SimpleDoubleProperty healthProperty = new SimpleDoubleProperty(this, "health");
    private SimpleBooleanProperty isGameOver = new SimpleBooleanProperty();
    private StringProperty inputTextProperty;

    public Player(int score, int health, SimpleIntegerProperty scoreProperty, SimpleDoubleProperty healthProperty,
                  SimpleBooleanProperty isGameOver, StringProperty inputTextProperty) {
        this.score = score;
        this.health = health;
        this.scoreProperty = scoreProperty;
        this.healthProperty = healthProperty;
        this.isGameOver = isGameOver;
        this.inputTextProperty = inputTextProperty;
    }

    public Player() {
        score = DEFAULT_SCORE_VALUE;
        scoreProperty.setValue(score);
        health = DEFAULT_HEALTH_VALUE;
        healthProperty.set(DEFAULT_HEALTH_PROPERTY_VALUE);
        isGameOver.set(false);
    }

    /**
     * Sets the player's input text as {@code inputTextProperty}
     * @param inputTextProperty The text property associated with the player.
     */
    public Player setInputAs(StringProperty inputTextProperty) {
        requireNonNull(inputTextProperty);
        this.inputTextProperty = inputTextProperty;
        return this;
    }

    /**
     * Sets the player's input text as {@code inputText}
     * @param inputText The text property associated with the player.
     */
    public Player setInputTextAs(String inputText) {
        requireNonNull(inputText);
        this.inputTextProperty.set(inputText);
        return this;
    }

    /**
     * Returns input text by player.
     */
    public String getInputText() {
        return inputTextProperty.get().strip();
    }

    /**
     * Increases this player's score by {@code score} if the game is not over.
     */
    public Player incrementScore(int score) {
        requireNonNull(score);
        checkArgument(score >= 0);
        if (isGameOver.get()) {
            return this;
        }
        this.score += score;
        scoreProperty.setValue(this.score);
        logger.info("score incremented by " + score + " to " + this.score);
        return this;
    }

    /**
     * Decreases this player's health by {@code health} if the game is not over.
     */
    public Player decrementHealth(int health) {
        requireNonNull(health);
        if (isGameOver.get()) {
            return this;
        }
        if (this.health - health <= 0) {
            isGameOver.set(true);
            this.health = 0;
            healthProperty.set(0);
            return this;
        }
        this.health -= health;
        healthProperty.set((double) this.health / DEFAULT_HEALTH_VALUE);
        logger.info("health decremented by " + health + " to " + this.health);
        return this;
    }

    public int getScore() {
        return score;
    }

    public int getHealth() {
        return health;
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

    public StringProperty getInputTextProperty() {
        return inputTextProperty;
    }

    /**
     * Returns true if both players of the same score and health.
     * This defines a weaker notion of equality between two players.
     */
    public boolean isSamePlayer(Player otherPlayer) {
        if (otherPlayer == this) {
            return true;
        }

        return otherPlayer != null
                && otherPlayer.getScore() == (this.getScore())
                && otherPlayer.getHealth() == (this.getHealth());
    }

    /**
     * Returns true if both players have the same score and health.
     * This defines a stronger notion of equality between two players.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Player)) {
            return false;
        }

        Player otherPlayer = (Player) other;
        return otherPlayer.getScore() == (this.getScore())
                && otherPlayer.getHealth() == (this.getHealth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, health);
    }

}
