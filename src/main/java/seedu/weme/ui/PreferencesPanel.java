package seedu.weme.ui;

import static seedu.weme.model.UserPrefs.DATA_FOLDER_PATH_KEY;
import static seedu.weme.model.UserPrefs.EXPORT_PATH_KEY;

import java.util.logging.Logger;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.weme.commons.core.LogsCenter;

/**
 * Panel containing the list of memes.
 */
public class PreferencesPanel extends UiPart<Region> {

    private static final String NO_PREFERENCES_SPECIFIED = "No preferences specified";
    private static final String FXML = "PreferencesPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PreferencesPanel.class);

    @FXML
    private Label exportPath;
    @FXML
    private Label dataFolderPath;

    public PreferencesPanel(ObservableMap<String, String> observableUserPreferences) {
        super(FXML);
        exportPath.setText("Export Path: "
                + observableUserPreferences.getOrDefault(EXPORT_PATH_KEY, NO_PREFERENCES_SPECIFIED));
        dataFolderPath.setText("Data Folder Path: "
                + observableUserPreferences.getOrDefault(DATA_FOLDER_PATH_KEY, NO_PREFERENCES_SPECIFIED));
    }

}
