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
    public static final Path DEFAULT_DATA_FILE_PATH = TestUtil.getFilePathInSandboxFolder("data/weme.json");
    public static final Path DEFAULT_MEME_IMAGE_PATH = TestUtil.getFilePathInSandboxFolder("data/memes");
    public static final Path DEFAULT_TEMPLATE_IMAGE_PATH = TestUtil.getFilePathInSandboxFolder("data/templates");

    private GuiSettings guiSettings;
    private Path dataFilePath;
    private Path memeImagePath;
    private Path templateImagePath;

    public UserPrefsBuilder() {
        guiSettings = DEFAULT_GUI_SETTINGS;
        dataFilePath = DEFAULT_DATA_FILE_PATH;
        memeImagePath = DEFAULT_MEME_IMAGE_PATH;
        templateImagePath = DEFAULT_TEMPLATE_IMAGE_PATH;
    }

    /**
     * Initializes the UserPrefsBuilder with the data of {@code userPrefsToCopy}.
     */
    public UserPrefsBuilder(ReadOnlyUserPrefs userPrefsToCopy) {
        guiSettings = userPrefsToCopy.getGuiSettings();
        dataFilePath = userPrefsToCopy.getDataFilePath();
        memeImagePath = userPrefsToCopy.getMemeImagePath();
        templateImagePath = userPrefsToCopy.getTemplateImagePath();
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
     * Sets the data file path of the {@code UserPrefs} that we are building.
     *
     * @param dataFilePath
     */
    public UserPrefsBuilder withDataFilePath(Path dataFilePath) {
        this.dataFilePath = dataFilePath.toFile().toPath();
        return this;
    }

    /**
     * Sets the meme image path of the {@code UserPrefs} that we are building.
     *
     * @param memeImagePath
     */
    public UserPrefsBuilder withMemeImagePath(Path memeImagePath) {
        this.memeImagePath = memeImagePath.toFile().toPath();
        return this;
    }

    /**
     * Sets the template image path of the {@code UserPrefs} that we are building.
     *
     * @param templateImagePath
     */
    public UserPrefsBuilder withTemplateImagePath(Path templateImagePath) {
        this.templateImagePath = templateImagePath.toFile().toPath();
        return this;
    }

    /**
     * Returns the {@code UserPrefs} that we have built.

     * @return the {@code UserPrefs} that we have built
     */
    public UserPrefs build() {
        UserPrefs ret = new UserPrefs();
        ret.setGuiSettings(guiSettings);
        ret.setDataFilePath(dataFilePath);
        ret.setMemeImagePath(memeImagePath);
        ret.setTemplateImagePath(templateImagePath);
        return ret;
    }

}
