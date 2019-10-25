package seedu.address.ui.views;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * A reusable settings card that has three labels.
 */
public class SettingsCard extends UiPart<Region> {
    private static final String FXML = "SettingsCard.fxml";
    private final Logger logger = LogsCenter.getLogger(SettingsCard.class);

    @FXML
    private AnchorPane settingCard;
    @FXML
    private Label settingsName;
    @FXML
    private Label optionOne;
    @FXML
    private Label optionTwo;

    public SettingsCard(Label settingsName, Label optionOne, Label optionTwo) {
        super(FXML);
        this.settingsName = settingsName;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
    }

}
