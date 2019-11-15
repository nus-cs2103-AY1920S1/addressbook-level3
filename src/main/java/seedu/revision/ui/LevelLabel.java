package seedu.revision.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class LevelLabel extends UiPart<Region> {

    private static final Logger logger = Logger.getLogger(LevelLabel.class.getName());
    private static final String FXML = "LevelLabel.fxml";

    @FXML
    private StackPane innerLevelPlaceholder;

    @FXML
    private Label levelLabel;

    /**
     * Initialises a LevelLabel based on the next level in the quiz.
     * @param nextLevel the next level in the quiz.
     */
    public LevelLabel(int nextLevel) {
        super(FXML);
        updateLevelLabel(nextLevel);
    }

    /**
     * Updates the {@code levelLabel}'s text and color according to the next level.
     * @param nextLevel the next level in the quiz.
     */
    public void updateLevelLabel(int nextLevel) {
        assert nextLevel > 0 && nextLevel < 4 : "Level should be between in the range [1,3]";
        Platform.runLater(() -> {
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
            default:
                logger.warning("invalid level selected");
                break;
            }
        });
    }

    public Label getLevelLabel() {
        return levelLabel;
    }
}
