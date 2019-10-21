package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.ui.UiPart;

import java.util.ArrayList;
import java.util.List;


public class SettingsPanel extends UiPart<Region> {
    private static final String FXML = "SettingsPanel.fxml";
    private static final String LABEL_STYLE = "-fx-font-size: 11pt;"
                                            + "-fx-font-family: \"Segoe UI SemiBold\";"
                                            + "-fx-opacity: 1;"
                                            + "-fx-padding: 0 20 0 0;";
    private static final String HIGHLIGHTED = LABEL_STYLE + "-fx-text-fill:rgba(85, 255, 68, 100);";
    private static final String UNHIGHLIGHTED = LABEL_STYLE + "-fx-text-fill:rgba(255, 255, 255, 100);";


    @FXML
    private HBox difficultyOptions;

    @FXML
    private HBox themeOptions;

    @FXML
    private HBox hintsOptions;

    public SettingsPanel(AppSettings currentSettings) {
        super(FXML);
        addOptions(difficultyOptions,  "EASY", "MEDIUM", "HARD");
        addOptions(themeOptions, "DARK", "LIGHT");
        addOptions(hintsOptions, "ON", "OFF");
        highlightChoice(difficultyOptions, currentSettings.getDefaultDifficulty().toString());
        highlightChoice(themeOptions, currentSettings.getDefaultTheme().toString());
        highlightChoice(hintsOptions, (currentSettings.getHintsEnabled() ? "ON" : "OFF"));
    }

    private void addOptions(HBox field, String... names) {
        List<Label> options = new ArrayList<>();
        for (String labelTitle: names) {
            Label option = new Label(labelTitle);
            option.setStyle(LABEL_STYLE);
            options.add(option);
        }
        field.getChildren().addAll(options);
    }

    public void highlightChoice(HBox field, String option) {
        int sizeOfList = field.getChildren().size();
        for (int i = 0; i < sizeOfList; i++) {
            Label child = (Label)field.getChildren().get(i);
            if (child.getStyle().equals(HIGHLIGHTED)) {
                unhighlightText(child);
                break;
            }
        }
        for (int i = 0; i < sizeOfList; i++) {
            Label child = (Label)field.getChildren().get(i);
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
        label.setStyle(UNHIGHLIGHTED);
    }

}
