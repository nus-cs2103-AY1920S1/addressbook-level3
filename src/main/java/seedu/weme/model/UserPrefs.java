package seedu.weme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import seedu.weme.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    public static final String EXPORT_PATH_KEY = "exportPath";
    public static final String DATA_FOLDER_PATH_KEY = "dataFolderPath";
    public static final String MEME_IMAGE_DIRECTORY_NAME = "memes";
    public static final String TEMPLATE_IMAGE_DIRECTORY_NAME = "templates";
    public static final String DATA_FILE_NAME = "weme.json";

    private GuiSettings guiSettings = new GuiSettings();
    private Path dataFolderPath = Paths.get("data");
    private Path exportFilePath = Paths.get(System.getProperty("user.home"));

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setDataFolderPath(newUserPrefs.getDataFolderPath());
        setExportPath(newUserPrefs.getExportPath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public Path getDataFolderPath() {
        return dataFolderPath;
    }

    public void setDataFolderPath(Path dataFolderPath) {
        requireNonNull(dataFolderPath);
        this.dataFolderPath = dataFolderPath;
    }

    @Override
    public Path getDataFilePath() {
        return dataFolderPath.resolve(DATA_FILE_NAME);
    }

    public Path getDefaultExportPath() {
        return exportFilePath;
    }

    @Override
    public Path getMemeImagePath() {
        return dataFolderPath.resolve(MEME_IMAGE_DIRECTORY_NAME);
    }

    @Override
    public Path getTemplateImagePath() {
        return dataFolderPath.resolve(TEMPLATE_IMAGE_DIRECTORY_NAME);
    }

    public ObservableMap<String, String> getObservableUserPreferences() {
        ObservableMap<String, String> observablePreferences = FXCollections.observableHashMap();
        observablePreferences.put(EXPORT_PATH_KEY, exportFilePath.toString());
        observablePreferences.put(DATA_FOLDER_PATH_KEY, dataFolderPath.toString());
        return observablePreferences;
    }


    public void setExportPath(Path exportFilePath) {
        requireNonNull(exportFilePath);
        this.exportFilePath = exportFilePath;
    }

    @Override
    public Path getExportPath() {
        return exportFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && dataFolderPath.equals(o.dataFolderPath)
                && exportFilePath.equals(o.exportFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, dataFolderPath, exportFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data folder location : " + dataFolderPath);
        sb.append("\nExport Path directory : " + exportFilePath);
        return sb.toString();
    }

}
