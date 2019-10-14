package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.statistics.GameStatistics;
import seedu.address.ui.UiPart;

/**
 * Panel containing the game result.
 */
public class GameResultPanel extends UiPart<Region> {
    private static final String FXML = "GameResultPanel.fxml";

    @FXML
    private Label title;

    @FXML
    private Label content;

    public GameResultPanel(GameStatistics gameStatistics) {
        super(FXML);
        title.setText(gameStatistics.getTitle());
        content.setText(gameStatistics.getData().toString());
    }
}
