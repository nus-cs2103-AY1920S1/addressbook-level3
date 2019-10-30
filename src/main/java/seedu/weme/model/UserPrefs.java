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
    public static final String DATA_FILE_PATH_KEY = "dataFilePath";
    public static final String MEME_IMAGE_PATH_KEY = "memeImagePath";
    public static final String TEMPLATE_IMAGE_PATH_KEY = "templateImagePath";

    private GuiSettings guiSettings = new GuiSettings();
    private Path dataFilePath = Paths.get("data" , "weme.json");
    private Path memeImagePath = Paths.get("data", "memes");
    private Path templateImagePath = Paths.get("data", "templates");
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
        setDataFilePath(newUserPrefs.getDataFilePath());
        setMemeImagePath(newUserPrefs.getMemeImagePath());
        setTemplateImagePath(newUserPrefs.getTemplateImagePath());
        setExportPath(newUserPrefs.getExportPath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(Path dataFilePath) {
        requireNonNull(dataFilePath);
        this.dataFilePath = dataFilePath;
    }

    public Path getDefaultExportPath() {
        return exportFilePath;
    }

    @Override
    public Path getMemeImagePath() {
        return memeImagePath;
    }

    public void setMemeImagePath(Path memeImagePath) {
        requireNonNull(memeImagePath);
        this.memeImagePath = memeImagePath;
    }

    public ObservableMap<String, String> getObservableUserPreferences() {
        ObservableMap<String, String> observablePreferences = FXCollections.observableHashMap();
        observablePreferences.put(EXPORT_PATH_KEY, exportFilePath.toString());
        observablePreferences.put(DATA_FILE_PATH_KEY, dataFilePath.toString());
        observablePreferences.put(MEME_IMAGE_PATH_KEY, memeImagePath.toString());
        observablePreferences.put(TEMPLATE_IMAGE_PATH_KEY, templateImagePath.toString());
        return observablePreferences;
    }

    @Override
    public Path getTemplateImagePath() {
        return templateImagePath;
    }

    public void setTemplateImagePath(Path templateImagePath) {
        requireNonNull(templateImagePath);
        this.templateImagePath = templateImagePath;
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
                && dataFilePath.equals(o.dataFilePath)
                && memeImagePath.equals(o.memeImagePath)
                && exportFilePath.equals(o.exportFilePath)
                && templateImagePath.equals(o.templateImagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, dataFilePath, memeImagePath, templateImagePath, exportFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + dataFilePath);
        sb.append("\nMemes image location : " + memeImagePath);
        sb.append("\nTemplate image location : " + templateImagePath);
        sb.append("\nExport Path directory : " + exportFilePath);
        return sb.toString();
    }

}
