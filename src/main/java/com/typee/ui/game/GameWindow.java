package com.typee.ui.game;

import com.typee.game.Player;
import com.typee.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        initialize(player);
        addStylesheet(root, "/view/GameWindow.css");
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

    /**
     * Creates a new game instance with a new player upon exit.
     */
    @Override
    @FXML
    public void handleExit() {
        getRoot().close();
        player = new Player();
        gameInstance = new GameWindow(new Stage(), player);
    }

    @FXML
    private void handleClear(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.SPACE) || keyEvent.getCode().equals(KeyCode.ENTER)) {
            playerInput.clear();
        }
    }

    /**
     * Initialize game play with PlayerInformation and GameBody, and bind player input text property and game over
     * property according to {@code player}.
     *
     * @param player The player that is currently on the game window.
     */
    private void initialize(Player player) {
        player.setInputAs(playerInput.textProperty());
        gameOver.visibleProperty().bind(player.getGameOverProperty());
        ObservableList<Node> nodes = gamePlay.getChildren();
        nodes.add(new PlayerInformation(player).getRoot());
        nodes.add(new GameBody(player).getRoot());
    }

    /**
     * Adds the stylesheet given by the resource {@code name} to the {@code stage}
     *
     * @param stage The stage that the stylesheet is being applied to.
     * @param name The name of the resource.
     */
    private void addStylesheet(Stage stage, String name) {
        String resource = this.getClass().getResource(name).toExternalForm();
        stage.getScene().getStylesheets().add(resource);
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
