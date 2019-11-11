package seedu.weme.testutil;

import java.awt.Point;
import java.nio.file.Path;

import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.UserPrefs;

/**
 * A utility class to help with building {@code UserPrefs}.
 */
public class UserPrefsBuilder {

    public static final GuiSettings DEFAULT_GUI_SETTINGS = new GuiSettings();
    public static final Path DEFAULT_DATA_FOLDER_PATH = TestUtil.getFilePathInSandboxFolder("data");

    private GuiSettings guiSettings;
    private Path dataFolderPath;

    public UserPrefsBuilder() {
        guiSettings = DEFAULT_GUI_SETTINGS;
        dataFolderPath = DEFAULT_DATA_FOLDER_PATH;
    }

    /**
     * Initializes the UserPrefsBuilder with the data of {@code userPrefsToCopy}.
     */
    public UserPrefsBuilder(ReadOnlyUserPrefs userPrefsToCopy) {
        guiSettings = userPrefsToCopy.getGuiSettings();
        dataFolderPath = userPrefsToCopy.getDataFolderPath();
    }

    /**
     * Sets the {@code GuiSettings} of the {@code UserPrefs} that we are building.
     *
     * @param guiSettings
     */
    public UserPrefsBuilder withGuiSettings(GuiSettings guiSettings) {
        double width = guiSettings.getWindowWidth();
        double height = guiSettings.getWindowHeight();
        Point coordinates = guiSettings.getWindowCoordinates();
        this.guiSettings = new GuiSettings(width, height, coordinates.x, coordinates.y);
        return this;
    }

    /**
     * Sets the data folder path of the {@code UserPrefs} that we are building.
     *
     * @param dataFolderPath
     */
    public UserPrefsBuilder withDataFolderPath(Path dataFolderPath) {
        this.dataFolderPath = dataFolderPath.toFile().toPath();
        return this;
    }

    /**
     * Returns the {@code UserPrefs} that we have built.

     * @return the {@code UserPrefs} that we have built
     */
    public UserPrefs build() {
        UserPrefs ret = new UserPrefs();
        ret.setGuiSettings(guiSettings);
        ret.setDataFolderPath(dataFolderPath);
        return ret;
    }

}
