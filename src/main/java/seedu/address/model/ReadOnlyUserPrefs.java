package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasMappings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getMooLahFilePath();

    AliasMappings getAliasMappings();

    Alias getAlias(String aliasName);

    boolean hasAlias(String aliasName);

    boolean aliasNameIsReserved(Alias alias);

    boolean aliasCommandWordIsAlias(Alias alias);

}
