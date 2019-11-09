package seedu.address.logic;

import seedu.address.achievements.logic.AchievementsLogic;
import seedu.address.address.logic.AddressBookLogic;
import seedu.address.calendar.logic.CalendarLogic;
import seedu.address.commons.core.GuiSettings;
import seedu.address.diaryfeature.logic.DiaryBookLogic;
import seedu.address.financialtracker.logic.FinancialTrackerLogic;
import seedu.address.itinerary.logic.ItineraryLogic;
import seedu.address.storage.Storage;


/**
 * API of the AddressBookLogic component
 */

public interface Logic extends GuiSettingsLogic {

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
     * Gets itinerary logic.
     *
     * @return Itinerary logic
     */
    ItineraryLogic getItineraryLogic();

    /**
     * Gets financial tracker logic.
     *
     * @return Financial tracker logic
     */
    FinancialTrackerLogic getFinancialTrackerLogic();

    /**
     * Gets main logic.
     *
     * @return Main logic
     */
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
