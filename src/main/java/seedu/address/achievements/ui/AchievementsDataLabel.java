package seedu.address.achievements.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays {@code dataTitle} and {@code dataValue} in a label.
 */
public class AchievementsDataLabel extends UiPart<Region> {

    private static final String FXML = "achievements/AchievementsDataLabel.fxml";

    @FXML
    private Label dataTitle;

    @FXML
    private Label dataValue;

    public AchievementsDataLabel(String dataTitle, String dataValue) {
        super(FXML);
        this.dataTitle.setText(dataTitle);
        this.dataValue.setText(dataValue);
    }
}
