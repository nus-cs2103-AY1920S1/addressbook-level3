package seedu.address.logic;

import java.util.function.Supplier;

import seedu.address.achievements.logic.AchievementsLogic;
import seedu.address.achievements.logic.AchievementsLogicManager;
import seedu.address.achievements.model.StatisticsModel;
import seedu.address.achievements.model.StatisticsModelManager;
import seedu.address.address.logic.AddressBookLogic;
import seedu.address.address.logic.AddressBookLogicManager;
import seedu.address.calendar.logic.CalendarLogic;
import seedu.address.commons.core.GuiSettings;
import seedu.address.diaryfeature.logic.DiaryBookLogic;
import seedu.address.financialtracker.logic.FinancialTrackerLogic;
import seedu.address.itinerary.logic.ItineraryLogic;
import seedu.address.model.Model;
import seedu.address.model.UserPrefsModel;
import seedu.address.storage.Storage;

/**
 * The main AddressBookLogicManager of the app.
 */
public class LogicManager implements Logic {

    private AddressBookLogic addressBookLogic;
    private AchievementsLogic achievementsLogic;
    private UserPrefsModel userPrefsModel;
    private DiaryBookLogic diaryLogic;
    private CalendarLogic calendarLogic;
    private FinancialTrackerLogic financialTrackerLogic;
    private ItineraryLogic itineraryLogic;
    private MainLogic mainLogic;
    private Storage storage;

    public LogicManager(Model model, Storage storage) {
        // overloaded AddressBook Logic Manager to pass main model in
        // main model is used to save gui settings
        this.userPrefsModel = model.getUserPrefsModel();
        this.addressBookLogic = new AddressBookLogicManager(model.getAddressBookModel(), storage);
        this.achievementsLogic = new AchievementsLogicManager(new Supplier<StatisticsModel>() {
            @Override
            public StatisticsModel get() {
                return new StatisticsModelManager(addressBookLogic.getStatistics(),
                        calendarLogic.getStatistics(),
                        diaryLogic.getStatistics(),
                        financialTrackerLogic.getStatistics(),
                        itineraryLogic.getStatistics());
            }
        });
        this.mainLogic = new MainLogicManager(userPrefsModel, storage);
        this.diaryLogic = new DiaryBookLogic();
        this.calendarLogic = new CalendarLogic();
        this.financialTrackerLogic = new FinancialTrackerLogic();
        this.itineraryLogic = new ItineraryLogic();
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public AddressBookLogic getAddressBookLogic() {
        return addressBookLogic;
    }

    @Override
    public AchievementsLogic getAchievementsLogic() {
        return achievementsLogic;
    }

    @Override
    public DiaryBookLogic getDiaryLogic() {
        return diaryLogic;
    }

    @Override
    public CalendarLogic getCalendarLogic() {
        return calendarLogic;
    }

    public FinancialTrackerLogic getFinancialTrackerLogic() {
        return this.financialTrackerLogic;
    }

    @Override
    public ItineraryLogic getItineraryLogic() {
        return this.itineraryLogic;
    }

    @Override
    public MainLogic getMainLogic() {
        return this.mainLogic;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefsModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        userPrefsModel.setGuiSettings(guiSettings);
    }
}
