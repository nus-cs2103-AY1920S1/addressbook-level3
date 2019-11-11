package seedu.address.achievements.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays progress in a {@link ProgressBar}.
 */
public class AchievementsProgressBar extends UiPart<Region> {

    private static final String FXML = "achievements/AchievementsProgressBar.fxml";

    @FXML
    private HBox progressBarPlaceholder;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progress;

    public AchievementsProgressBar(double progress) {
        super(FXML);
        progressBar.setProgress(progress);
        this.progress.setText(String.format("%.1f%%", progress * 100));
    }
}
