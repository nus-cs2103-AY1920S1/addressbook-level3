package budgetbuddy.model;

import java.nio.file.Path;

import budgetbuddy.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getLoansFilePath();

    Path getRuleFilePath();

    Path getScriptsPath();

    Path getAccountsFilePath();

}
