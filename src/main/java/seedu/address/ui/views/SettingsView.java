package seedu.address.ui.views;

import java.util.logging.Logger;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.settings.ClockFormat;
import seedu.address.model.settings.Theme;
import seedu.address.ui.UiPart;

/**
 * Layout displaying the current settings in +Work.
 */
public class SettingsView extends UiPart<Region> {
    private static final String FXML = "SettingsWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(SettingsView.class);

    private final String THEME_LABEL = "THEME";
    private final String CLOCK_LABEL = "CLOCK-FORMAT";

    @FXML
    private VBox vBox;
    @FXML
    private AnchorPane themeSetting;
    @FXML
    private AnchorPane clockSetting;
    @FXML
    private Label themeLabel;
    @FXML
    private Label themeLabelDark;
    @FXML
    private Label themeLabelLight;
    @FXML
    private Label clockLabel;
    @FXML
    private Label clockLabelTwentyFour;
    @FXML
    private Label clockLabelTwelve;

    public SettingsView(Theme theme, ClockFormat clockFormat) {
        super(FXML);
        themeLabel.setText(THEME_LABEL);
        themeLabelDark.setText("DARK");
        themeLabelLight.setText("LIGHT");
        clockLabel.setText(CLOCK_LABEL);
        clockLabelTwelve.setText("12HR");
        clockLabelTwentyFour.setText("24HR");
    }

}
