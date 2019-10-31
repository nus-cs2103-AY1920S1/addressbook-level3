package com.typee.ui.game;

import com.typee.game.Player;
import com.typee.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class which handles game window.
 */
public class GameWindow extends UiPart<Stage> {

    private static final String FXML = "GameWindow.fxml";
    private static GameWindow gameInstance;
    private static Player player;

    @FXML
    private AnchorPane gamePlay;

    @FXML
    private TextField playerInput;

    @FXML
    private Label gameOver;

    /**
     * Creates a new game window.
     *
     * @param root Stage that is the root of the window.
     * @param player User that plays the game.
     */
    private GameWindow(Stage root, Player player) {
        super(FXML, root);
        player.setInputAs(playerInput.textProperty());
        gameOver.visibleProperty().bind(player.getGameOverProperty());
        gamePlay.getChildren().addAll(new PlayerInformation(player).getRoot(), new GameBody(player).getRoot());
        root.getScene().getStylesheets().addAll(this.getClass().getResource("/view/GameWindow.css").toExternalForm());
    }

    /**
     * Creates a game instance in new Window with a new player if there is no existing game instance or if the current
     * game is over.
    */
    public static GameWindow getInstance() {
        if (gameInstance == null || player.getGameOverProperty().get()) {
            player = new Player();
            gameInstance = new GameWindow(new Stage(), player);
        }
        return gameInstance;
    }

    @FXML
    private void handleExit() {
        getRoot().close();
    }

    @FXML
    private void handlePlayerInput() {
        playerInput.clear();
    }

    /**
     * Shows the window.
     */
    public void show() {
        getRoot().show();
    }

    /**
     * Returns true if the window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
