package seedu.main.model;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

/**
 * Represents the in-memory addressBookModel of the address book data.
 */
public class MainModelManager implements MainModel {
    private static final Logger logger = LogsCenter.getLogger(MainModelManager.class);

    private UserPrefs userPrefs;

    public MainModelManager() {
        this.userPrefs = new UserPrefs();
    }

    public MainModelManager(ReadOnlyUserPrefs userPrefs) {
        this.userPrefs = new UserPrefs(userPrefs);
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof MainModelManager)) {
            return false;
        }

        // state check
        MainModelManager other = (MainModelManager) obj;
        return userPrefs.equals(other.userPrefs);
    }
}
