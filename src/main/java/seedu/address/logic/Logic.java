package seedu.address.logic;

import seedu.address.achievements.logic.AchievementsLogic;
import seedu.address.address.logic.AddressBookLogic;
import seedu.address.calendar.logic.CalendarLogic;
import seedu.address.commons.core.GuiSettings;
import seedu.address.diaryfeature.logic.DiaryBookLogic;
import seedu.address.storage.Storage;

/**
 * API of the AddressBookLogic component
 */

public interface Logic {

    Storage getStorage();

    /**
     * Gets address book logic.
     *
     * @return Address book logic
     */
    AddressBookLogic getAddressBookLogic();

    /**
     * Gets achievements logic.
     *
     * @return Achievement logic
     */
    AchievementsLogic getAchievementsLogic();

    /**
     * Gets diary logic.
     *
     * @return Diary logic
     */
    DiaryBookLogic getDiaryLogic();

    /**
     * Gets calendar logic.
     *
     * @return Calendar logic
     */
    CalendarLogic getCalendarLogic();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
