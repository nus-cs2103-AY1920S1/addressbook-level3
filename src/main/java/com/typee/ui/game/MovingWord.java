package com.typee.ui.game;

import java.util.Random;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.game.Player;
import com.typee.game.Words;
import com.typee.ui.UiPart;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Controller class which handles the game's moving words which player can type.
 */
public class MovingWord extends UiPart<Pane> {

    private static final Logger logger = LogsCenter.getLogger(MovingWord.class);
    private static final String FXML = "MovingWord.fxml";
    private static final Random random = new Random();
    private static final double LOWER_BOUND = 520;
    private static final int DECREMENT_VALUE = 20;
    private static final int SCORE_MULTIPLIER = 100;
    private static final int WINDOW_BOUNDARY = 500;
    private Pane parent;
    private Player player;
    private double fallingRate = 2.0;
    private String word;
    private AnimationTimer animationTimer;

    public MovingWord(double fallingRate, Pane parent, Player player) {
        super(FXML);
        this.parent = parent;
        this.player = player;
        this.fallingRate = fallingRate;
        word = Words.get(random.nextInt(Words.SIZE));
        setXCoordinate();
        parent.getChildren().add(getRoot());
        continuouslyUpdate();
    }

    private void stopAnimation() {
        animationTimer.stop();
    }

    private void setXCoordinate() {
        getRoot().setLayoutX(random.nextInt(WINDOW_BOUNDARY));
    }

    /**
     * Removes the moving word from the game window.
     */
    private void disappear() {
        parent.getChildren().remove(getRoot());
        word = null;
    }

    /**
     * Updates the player's score and health according to player's input.
     */
    private void update() {
        if (player.getGameOverProperty().get()) {
            disappear();
            return;
        }
        incrementYCoordinateBy(fallingRate);
        highlightWords();
        if (getRoot().getLayoutY() > LOWER_BOUND) {
            stopAnimation();
            player.decrementHealth(DECREMENT_VALUE);
            disappear();
        } else if (player.getInputText().equals(word)) {
            stopAnimation();
            player.incrementScore(word.length() * SCORE_MULTIPLIER);
            player.setInputAs("");
            disappear();
        }
    }

    /**
     * Converts moving word into text flow with or without CSS highlighting depending on what the player types, before
     * adding the words to the main pane.
     */
    private void highlightWords() {
        ObservableList<Node> words = getRoot().getChildren();
        words.clear();
        Node highlightedNode = TextHighlighter.convertToTextFlowUsing(player.getInputText(), word);
        Node nodeWithoutHighlight = TextHighlighter.convertToTextFlowUsing(word);
        words.add(highlightedNode);
        words.add(nodeWithoutHighlight);
    }

    private void incrementYCoordinateBy(double fallingRate) {
        double currentYCoordinate = getRoot().getLayoutY();
        getRoot().setLayoutY(currentYCoordinate + fallingRate);
    }

    /**
     * Ensures the player's score and health is updated according to player's input by continuously updating.
     */
    private void continuouslyUpdate() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        animationTimer.start();
    }
}
