package seedu.address.ui.views;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * A reusable settings card that has three labels.
 */
public class SettingsCard extends UiPart<AnchorPane> {
    private static final String FXML = "SettingsCard.fxml";
    private final Logger logger = LogsCenter.getLogger(SettingsCard.class);

    @FXML
    private AnchorPane settingCard;
    @FXML
    private Label settingName;
    @FXML
    private Label optionOne;
    @FXML
    private Label optionTwo;

    public SettingsCard(Label settingName, Label optionOne, Label optionTwo) {
        super(FXML);
        this.settingName = settingName;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        settingCard.getChildren().addAll(settingName, optionOne, optionTwo);
    }

}
