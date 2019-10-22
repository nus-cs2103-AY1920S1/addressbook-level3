package seedu.address.ui.modules;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.ui.UiPart;

/**
 * Panel that displays the current user settings.
 */
public class SettingsPanel extends UiPart<Region> {
    private static final String FXML = "SettingsPanel.fxml";
    private static final String LABEL_STYLE = "-fx-font-size: 11pt;"
                                            + "-fx-font-family: \"Segoe UI SemiBold\";"
                                            + "-fx-opacity: 0.5;"
                                            + "-fx-padding: 20 20 20 0;"
                                            + "-fx-text-fill: rgba(255, 255, 255, 100);";
    private static final String HIGHLIGHTED = LABEL_STYLE + "-fx-text-fill: rgba(85, 255, 68, 100);"
                                            + "-fx-opacity: 0.9";
    private AppSettings appSettings;


    @FXML
    private HBox difficultyOptions;

    @FXML
    private HBox themeOptions;

    @FXML
    private HBox hintsOptions;

    public SettingsPanel(AppSettings currentSettings) {
        super(FXML);
        addOptions(difficultyOptions, "EASY", "MEDIUM", "HARD");
        addOptions(themeOptions, "DARK", "LIGHT");
        addOptions(hintsOptions, "ON", "OFF");
        this.appSettings = currentSettings;
        updateSettings();
    }

    /**
     * Adds options to the field specified.
     *
     * @param field The settings field to add options to.
     * @param names The names of the options to add.
     */
    private void addOptions(HBox field, String... names) {
        List<Label> options = new ArrayList<>();
        for (String labelTitle: names) {
            Label option = new Label(labelTitle);
            option.setStyle(LABEL_STYLE);
            options.add(option);
        }
        field.getChildren().addAll(options);
    }

    /**
     * Highlights the chosen option.
     *
     * @param field The settings field that is being chosen from.
     * @param option The option that is chosen.
     */
    public void highlightChoice(HBox field, String option) {
        int sizeOfList = field.getChildren().size();
        for (int i = 0; i < sizeOfList; i++) {
            Label child = (Label) field.getChildren().get(i);
            if (child.getStyle().equals(HIGHLIGHTED)) {
                unhighlightText(child);
                break;
            }
        }
        for (int i = 0; i < sizeOfList; i++) {
            Label child = (Label) field.getChildren().get(i);
            if (child.getText().equals(option)) {
                highlightText(child);
                break;
            }
        }
    }

    private void highlightText(Label label) {
        label.setStyle(HIGHLIGHTED);
    }

    private void unhighlightText(Label label) {
        label.setStyle(LABEL_STYLE);
    }

    /**
     * Updates the panel to match the current state of settings.
     */
    public void updateSettings() {
        highlightChoice(difficultyOptions, appSettings.getDefaultDifficulty().toString());
        highlightChoice(themeOptions, appSettings.getDefaultTheme().toString());
        highlightChoice(hintsOptions, (appSettings.getHintsEnabled() ? "ON" : "OFF"));
    }
}
