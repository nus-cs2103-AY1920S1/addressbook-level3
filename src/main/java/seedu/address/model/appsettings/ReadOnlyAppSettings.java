package seedu.address.model.appsettings;

import java.nio.file.Path;

/**
 * Unmodifiable view of app settings.
 */
public interface ReadOnlyAppSettings {

    Path getAppSettingsFilePath();

    DifficultyEnum getDefaultDifficulty();

    ThemeEnum getDefaultTheme();

    boolean getHintsEnabled();

}
