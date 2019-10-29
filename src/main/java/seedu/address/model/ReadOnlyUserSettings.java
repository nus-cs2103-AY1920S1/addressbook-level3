package seedu.address.model;

import java.nio.file.Path;

import seedu.address.model.settings.ClockFormat;
import seedu.address.model.settings.Theme;

/**
 * Unmodifiable view of user settings.
 */
public interface ReadOnlyUserSettings {

    Path getUserSettingsFilePath();

    Theme getTheme();

    ClockFormat getClockFormat();

}
