package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.Alias;
import seedu.address.commons.core.AliasMappings;
import seedu.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    AliasMappings getAliasMappings();

    Alias getAlias(String aliasName);

    boolean hasAlias(String aliasName);

    boolean aliasNameIsReserved(Alias alias);

    boolean aliasCommandWordIsAlias(Alias alias);

}
