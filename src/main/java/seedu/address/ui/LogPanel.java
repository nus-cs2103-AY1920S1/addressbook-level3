package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Screen;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class LogPanel extends UiPart<Region> {

    private static final String FXML = "LogPanel.fxml";

    @FXML
    private VBox boxLog;

    @FXML
    private ScrollPane scrollLog;

    public LogPanel() {
        super(FXML);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = primaryScreenBounds.getHeight();
        double screenWidth = primaryScreenBounds.getWidth();

        boxLog.setPrefHeight(screenHeight - screenHeight / 5);
        boxLog.setPrefWidth(screenWidth / 2 - MainWindow.WIDTH_PADDING);

    }

    public void createLogBox(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        LogBox logBox = new LogBox(feedbackToUser);
        boxLog.getChildren().add(logBox.getRoot());
    }

}
