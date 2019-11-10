package seedu.pluswork.ui.views;

import java.util.logging.Logger;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.ui.UiPart;

/**
 * Layout displaying the current settings in +Work.
 * Responsive highlighting is adapted from CS2103 course mate (found using RepoSense)
 * https://github.com/AY1920S1-CS2103T-T11-2/main/blame/master/src/main/java/seedu/address/ui/modules/SettingsPanel.java
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
