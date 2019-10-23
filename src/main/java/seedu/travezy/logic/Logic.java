package seedu.travezy.logic;

import seedu.travezy.achievements.logic.AchievementsLogic;
import seedu.travezy.commons.core.GuiSettings;
import seedu.travezy.address.logic.AddressBookLogic;
import seedu.travezy.storage.Storage;

/**
 * API of the AddressBookLogic component
 */

public interface Logic {

    public Storage getStorage();

    public AddressBookLogic getAddressBookLogic();

    public AchievementsLogic getAchievementsLogic();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
