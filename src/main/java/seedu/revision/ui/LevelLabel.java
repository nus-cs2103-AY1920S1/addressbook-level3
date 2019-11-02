package seedu.revision.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static java.util.Objects.requireNonNull;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class LevelLabel extends UiPart<Region> {

    private static final String FXML = "LevelLabel.fxml";

    @FXML
    private StackPane innerLevelPlaceholder;

    @FXML
    private Label levelLabel;

    public LevelLabel(int nextLevel) {
        super(FXML);
        updateLevelLabel(nextLevel);
    }

    private void updateLevelLabel(int nextLevel) {
        switch (nextLevel) {
        case 1:
            levelLabel.setText("Level 1");
            innerLevelPlaceholder.setStyle("-fx-background-color: #5D5D5A;");
            break;
        case 2:
            levelLabel.setText("Level 2");
            innerLevelPlaceholder.setStyle("-fx-background-color: #ff8264;");
            break;
        case 3:
            levelLabel.setText("Level 3");
            innerLevelPlaceholder.setStyle("-fx-background-color: #f73859;");
            break;
        }

    }

}
