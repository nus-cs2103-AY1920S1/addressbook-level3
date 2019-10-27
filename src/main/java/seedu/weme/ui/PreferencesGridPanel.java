package seedu.weme.ui;

import static seedu.weme.model.UserPrefs.DATA_FILE_PATH_KEY;
import static seedu.weme.model.UserPrefs.EXPORT_PATH_KEY;
import static seedu.weme.model.UserPrefs.MEME_IMAGE_PATH_KEY;
import static seedu.weme.model.UserPrefs.TEMPLATE_IMAGE_PATH_KEY;

import java.util.logging.Logger;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.weme.commons.core.LogsCenter;

/**
 * Panel containing the list of memes.
 */
public class PreferencesGridPanel extends UiPart<Region> {

    private static final String NO_PREFERENCES_SPECIFIED = "No preferences specified";
    private static final String FXML = "PreferencesGridPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PreferencesGridPanel.class);

    @FXML
    private Label exportPath;
    @FXML
    private Label dataFilePath;
    @FXML
    private Label memeImagePath;
    @FXML
    private Label templateImagePath;

    public PreferencesGridPanel(ObservableMap<String, String> observableUserPreferences) {
        super(FXML);
        exportPath.setText("Export Path: "
                + observableUserPreferences.getOrDefault(EXPORT_PATH_KEY, NO_PREFERENCES_SPECIFIED));
        dataFilePath.setText("Data File Path: "
                + observableUserPreferences.getOrDefault(DATA_FILE_PATH_KEY, NO_PREFERENCES_SPECIFIED));
        memeImagePath.setText("Meme Image Path: "
                + observableUserPreferences.getOrDefault(MEME_IMAGE_PATH_KEY, NO_PREFERENCES_SPECIFIED));
        templateImagePath.setText("Template Image Path: "
                + observableUserPreferences.getOrDefault(TEMPLATE_IMAGE_PATH_KEY, NO_PREFERENCES_SPECIFIED));
    }

}
