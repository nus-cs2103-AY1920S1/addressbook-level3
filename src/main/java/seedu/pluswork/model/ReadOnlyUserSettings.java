package seedu.pluswork.model;

import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;

import java.nio.file.Path;

/**
 * Unmodifiable view of user settings.
 */
public interface ReadOnlyUserSettings {

    Path getUserSettingsFilePath();

    Theme getTheme();

    ClockFormat getClockFormat();

}
