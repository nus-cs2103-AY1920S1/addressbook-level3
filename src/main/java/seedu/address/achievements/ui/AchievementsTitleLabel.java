package seedu.address.achievements.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays {@code title} of {@link AchievementsCard}.
 */
public class AchievementsTitleLabel extends UiPart<Region> {

    private static final String FXML = "achievements/AchievementsTitleLabel.fxml";

    @FXML
    private Label cardTitle;

    public AchievementsTitleLabel(String cardTitle) {
        super(FXML);
        this.cardTitle.setText(cardTitle);
    }
}
