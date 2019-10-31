package seedu.savenus.model.userprefs;

import java.nio.file.Path;

import seedu.savenus.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getMenuFilePath();

    Path getPurchaseHistoryFilePath();

    Path getSavingsAccountFilePath();

    Path getWalletFilePath();

    Path getSortFilePath();

    Path getAliasFilePath();
}
