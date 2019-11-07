package seedu.pluswork.model;

import java.nio.file.Path;

import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;

/**
 * Unmodifiable view of user settings.
 */
public interface ReadOnlyUserSettings {

    Path getUserSettingsFilePath();

    Theme getTheme();

    ClockFormat getClockFormat();

}
