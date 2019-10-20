package seedu.address.model.appsettings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents User's preferences.
 */
public class AppSettings implements ReadOnlyAppSettings {

    private Path appSettingsFilePath = Paths.get("data" , "appsettings.json");

    //Settings fields
    private DifficultyEnum defaultDifficulty = DifficultyEnum.EASY;
    private ThemeEnum defaultTheme = ThemeEnum.DARK;
    private boolean hintsEnabled = false;


    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public AppSettings() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public AppSettings(ReadOnlyAppSettings userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newAppSettings}.
     */
    public void resetData(ReadOnlyAppSettings newAppSettings) {
        requireNonNull(newAppSettings);
        setAppSettingsFilePath(newAppSettings.getAppSettingsFilePath());
        setDefaultDifficulty(newAppSettings.getDefaultDifficulty());
        setDefaultTheme(newAppSettings.getDefaultTheme());
        setHintsEnabled(newAppSettings.getHintsEnabled());
    }

    @Override
    public Path getAppSettingsFilePath() {
        return appSettingsFilePath;
    }

    public void setAppSettingsFilePath(Path appSettingsFilePath) {
        requireNonNull(appSettingsFilePath);
        this.appSettingsFilePath = appSettingsFilePath;
    }

    @Override
    public DifficultyEnum getDefaultDifficulty() {
        return defaultDifficulty;
    }

    public void setDefaultDifficulty(DifficultyEnum defaultDifficulty) {
        requireNonNull(defaultDifficulty);
        this.defaultDifficulty = defaultDifficulty;
    }

    @Override
    public ThemeEnum getDefaultTheme() {
        return defaultTheme;
    }

    public void setDefaultTheme(ThemeEnum defaultTheme) {
        requireNonNull(defaultTheme);
        this.defaultTheme = defaultTheme;
    }

    @Override
    public boolean getHintsEnabled() {
        return false;
    }

    public void setHintsEnabled(boolean enabled) {
        requireNonNull(enabled);
        this.hintsEnabled = enabled;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AppSettings)) { //this handles null as well.
            return false;
        }

        AppSettings o = (AppSettings) other;

        return defaultDifficulty.equals(o.defaultDifficulty)
                && defaultTheme.equals(o.defaultTheme)
                && hintsEnabled == o.hintsEnabled
                && appSettingsFilePath.equals(o.appSettingsFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appSettingsFilePath);
    }

    @Override
    public String toString() {
        return "Local data file location" + appSettingsFilePath;
    }

}
