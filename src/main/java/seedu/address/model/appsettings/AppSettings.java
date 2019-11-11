package seedu.address.model.appsettings;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents User's preferences.
 */
public class AppSettings implements ReadOnlyAppSettings {

    private Path appSettingsFilePath = Paths.get("data" , "appsettings.json");

    //Settings fields
    private DifficultyEnum defaultDifficulty = DifficultyEnum.EASY;
    private ThemeEnum defaultTheme = ThemeEnum.DARK;
    private boolean hintsEnabled = false;
    private int avatarId = 0;


    /**
     * Creates a {@code AppSettings} with default values.
     */
    public AppSettings() {}

    /**
     * Creates a {@code AppSettings} with the prefs in {@code appSettings}.
     */
    public AppSettings(ReadOnlyAppSettings appSettings) {
        this();
        resetData(appSettings);
    }

    /**
     * Resets the existing data of this {@code AppSettings} with {@code newAppSettings}.
     */
    private void resetData(ReadOnlyAppSettings newAppSettings) {
        try {
            requireNonNull(newAppSettings);
            setAppSettingsFilePath(newAppSettings.getAppSettingsFilePath());
            setDefaultDifficulty(newAppSettings.getDefaultDifficulty());
            setDefaultTheme(newAppSettings.getDefaultTheme());
            setHintsEnabled(newAppSettings.getHintsEnabled());
            setAvatarId(newAppSettings.getAvatarId());
        } catch (NullPointerException ex) {
            System.out.println("Please do not try to break the files directly. It still works but it will throw "
                    + "all kinds of warnings.");
        }
    }

    @Override
    public Path getAppSettingsFilePath() {
        return appSettingsFilePath;
    }

    private void setAppSettingsFilePath(Path appSettingsFilePath) {
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
        return hintsEnabled;
    }

    public void setHintsEnabled(boolean enabled) {
        requireNonNull(enabled);
        this.hintsEnabled = enabled;
    }

    @Override
    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int i) {
        requireNonNull(i);
        this.avatarId = i;
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
