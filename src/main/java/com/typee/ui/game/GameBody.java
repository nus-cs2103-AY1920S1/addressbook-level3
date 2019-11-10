package com.typee.ui.game;

import com.typee.game.Player;
import com.typee.ui.UiPart;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

/**
 * Controller class which handles the part of the game window that has moving words which player can type.
 */
public class GameBody extends UiPart<Pane> {

    private static final String FXML = "GameBody.fxml";
    private AnimationTimer animationTimer;
    private Player player;
    private double fallingRate = 2.0;

    public GameBody(Player player) {
        super(FXML);
        this.player = player;
        loopWords();
    }

    /**
     * Creates a loop of falling words.
     */
    private void loopWords() {
        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= Integer.MAX_VALUE) {
                    new MovingWord(fallingRate, getRoot(), player);
                    fallingRate += 0.1;
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();
    }
}
