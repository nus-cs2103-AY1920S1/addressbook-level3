package seedu.address.ui.views;

import java.util.logging.Logger;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private static final String LABEL_UNHIGHLIGHTED = "label-unhighlighted";
    private static final String LABEL_HIGHLIGHTED = "label-highlighted";

    private final Logger logger = LogsCenter.getLogger(SettingsView.class);

    private Theme theme;
    private ClockFormat clockFormat;

    @FXML
    private AnchorPane themeSetting;
    @FXML
    private AnchorPane clockSetting;
    @FXML
    private Label themeLabelDark;
    @FXML
    private Label themeLabelLight;
    @FXML
    private Label clockLabelTwentyFour;
    @FXML
    private Label clockLabelTwelve;

    public SettingsView(Theme theme, ClockFormat clockFormat) {
        super(FXML);
        handleTheme();
        handleClock();
        this.theme = theme;
        this.clockFormat = clockFormat;
        updateHighlights();
    }

    private void handleTheme() {
        themeLabelLight.setText("LIGHT");
        themeLabelDark.setText("DARK");
    }

    private void handleClock() {
        clockLabelTwelve.setText("12HR");
        clockLabelTwentyFour.setText("24HR");
    }

    /**
     * Highlights the chosen option.
     *
     * @param option the option that is chosen
     */
    private void highlightChoice(AnchorPane setting, String option) {
        setting.getChildren().forEach(child -> unhighlightLabel((Label) child));
        Stream<Node> nodeStream = setting.getChildren().stream();
        nodeStream.filter(child -> ((Label) child).getText().equals(option))
                .forEach(child -> highlightLabel((Label) child));
    }

    private void highlightLabel(Label label) {
        label.getStyleClass().clear();
        label.getStyleClass().add(LABEL_HIGHLIGHTED);
    }

    private void unhighlightLabel(Label label) {
        label.getStyleClass().clear();
        label.getStyleClass().add(LABEL_UNHIGHLIGHTED);
    }

    /**
     * Updates the panel to match the current state of settings.
     */
    public void updateHighlights() {
        highlightChoice(themeSetting, theme.toString());
        highlightChoice(clockSetting, clockFormat.getDisplayName());
    }

}
