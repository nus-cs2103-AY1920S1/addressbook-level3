package seedu.address.ui.views;

import java.util.logging.Logger;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
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
    private AnchorPane themeSetting;
    @FXML
    private AnchorPane clockSetting;

    // All possible labels
    private Label themeLabel = new Label(THEME_LABEL);
    private Label themeLabelDark = new Label("DARK");
    private Label themeLabelLight = new Label("LIGHT");
    private Label clockLabel = new Label(CLOCK_LABEL);
    private Label clockLabelTwentyFour = new Label("24HR");
    private Label clockLabelTwelve = new Label("12HR");

    public SettingsView(Theme theme, ClockFormat clockFormat) {
        super(FXML);

        handleTheme(theme);

        handleClock(clockFormat);
    }

    private void handleTheme(Theme theme) {
        themeSetting.getChildren().add(new SettingsCard(themeLabel, themeLabelLight, themeLabelDark).getRoot());
    }

    private void handleClock(ClockFormat clockFormat) {
        clockSetting.getChildren().add(new SettingsCard(clockLabel, clockLabelTwelve, clockLabelTwentyFour).getRoot());
    }

}
