package seedu.address.logic;

import seedu.address.achievements.logic.AchievementsLogic;
import seedu.address.address.logic.AddressBookLogic;
import seedu.address.commons.core.GuiSettings;
import seedu.address.diaryfeature.logic.DiaryBookLogic;

/**
 * API of the AddressBookLogic component
 */

public interface Logic extends GuiSettingsLogic {

    public AddressBookLogic getAddressBookLogic();

    public AchievementsLogic getAchievementsLogic();

    public DiaryBookLogic getDiaryLogic();

    public MainLogic getMainLogic();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set gui settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
