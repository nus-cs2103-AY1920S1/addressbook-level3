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
    private static final String LABEL_STYLE = "label-unhighlighted";
    private static final String HIGHLIGHTED = "label-highlighted";
    private AppSettings appSettings;


    @FXML
    private HBox difficultyOptions;

    @FXML
    private HBox themeOptions;

    @FXML
    private HBox hintsOptions;

    @FXML
    private HBox avatarId;

    public SettingsPanel(AppSettings currentSettings) {
        super(FXML);
        this.appSettings = currentSettings;
        addOptions(difficultyOptions, "EASY", "MEDIUM", "HARD");
        addOptions(themeOptions, "DARK", "LIGHT");
        addOptions(hintsOptions, "ON", "OFF");
        setOption(avatarId, currentSettings.getAvatarId() + "");
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
            option.getStyleClass().add(LABEL_STYLE);
            options.add(option);
        }
        field.getChildren().addAll(options);
    }

    /**
     * For settings that have a range of values to choose from.
     *
     * @param name The name to set the option to.
     * @param field The settings field to set the options in.
     */
    private void setOption(HBox field, String name) {
        field.getChildren().clear();
        Label newOption = new Label(name);
        newOption.getStyleClass().add(HIGHLIGHTED);
        field.getChildren().add(newOption);
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
            if (child.getStyleClass().contains(HIGHLIGHTED)) {
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
        label.getStyleClass().remove(LABEL_STYLE);
        label.getStyleClass().add(HIGHLIGHTED);
    }

    private void unhighlightText(Label label) {
        label.getStyleClass().remove(HIGHLIGHTED);
        label.getStyleClass().add(LABEL_STYLE);
    }

    /**
     * Updates the panel to match the current state of settings.
     */
    public void updateSettings() {
        highlightChoice(difficultyOptions, appSettings.getDefaultDifficulty().toString());
        highlightChoice(themeOptions, appSettings.getDefaultTheme().toString());
        highlightChoice(hintsOptions, (appSettings.getHintsEnabled() ? "ON" : "OFF"));

        int avatarId = appSettings.getAvatarId();
        setOption(this.avatarId, avatarId == 0 ? "RANDOM" : avatarId + "");
    }
}
