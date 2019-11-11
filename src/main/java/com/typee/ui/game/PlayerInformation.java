package com.typee.ui.game;

import com.typee.game.Player;
import com.typee.ui.UiPart;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

/**
 * Controller class which handles the part of the game window that has the player's score and health.
 */
public class PlayerInformation extends UiPart<StackPane> {

    private static final String FXML = "PlayerInformation.fxml";

    @FXML
    private Label score;

    @FXML
    private ProgressBar health;

    private Player player;

    public PlayerInformation(Player player) {
        super(FXML);
        initialize(player);
    }

    private void initialize(Player player) {
        health.progressProperty().bind(player.getHealthProperty());
        score.textProperty().bind(Bindings.convert(player.getScoreProperty()));
    }
}
