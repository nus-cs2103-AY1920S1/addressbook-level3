package seedu.address.logic;

import seedu.address.achievements.logic.AchievementsLogic;
import seedu.address.address.logic.AddressBookLogic;
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

    AddressBookLogic getAddressBookLogic();

    AchievementsLogic getAchievementsLogic();

    DiaryBookLogic getDiaryLogic();

    ItineraryLogic getItineraryLogic();

    FinancialTrackerLogic getFinancialTrackerLogic();

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
