package seedu.ifridge.model;

import java.nio.file.Path;

import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.commons.core.IFridgeSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    IFridgeSettings getIFridgeSettings();

    Path getGroceryListFilePath();

    Path getWasteArchiveFilePath();

    Path getShoppingListFilePath();

    Path getBoughtListFilePath();
}
