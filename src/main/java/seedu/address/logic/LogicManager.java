package seedu.address.logic;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.achievements.logic.AchievementsLogic;
import seedu.address.achievements.logic.AchievementsLogicManager;
import seedu.address.address.logic.AddressBookLogic;
import seedu.address.address.logic.AddressBookLogicManager;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.diaryfeature.logic.DiaryBookLogic;
import seedu.address.financialtracker.logic.FinancialTrackerLogic;
import seedu.address.itinerary.logic.ItineraryLogic;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserPrefsModel;
import seedu.address.storage.Storage;

/**
 * The main AddressBookLogicManager of the app.
 */
public class LogicManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private AddressBookLogic addressBookLogic;
    private AchievementsLogic achievementsLogic;
    private UserPrefsModel userPrefsModel;
    private DiaryBookLogic diaryLogic;
    private FinancialTrackerLogic financialTrackerLogic;
    private ItineraryLogic itineraryLogic;
    private MainLogic mainLogic;
    private Storage storage;

    public LogicManager(Model model, Storage storage) {
        // overloaded AddressBook Logic Manager to pass main model in
        // main model is used to save gui settings
        this.userPrefsModel = model.getUserPrefsModel();
        this.addressBookLogic = new AddressBookLogicManager(model.getAddressBookModel(), storage);
        this.achievementsLogic = new AchievementsLogicManager(model.statisticsModelSupplier());
        this.mainLogic = new MainLogicManager(userPrefsModel, storage);
        this.diaryLogic = new DiaryBookLogic();
        this.financialTrackerLogic = new FinancialTrackerLogic();
        this.itineraryLogic = new ItineraryLogic();
      
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    public AddressBookLogic getAddressBookLogic() {
        return addressBookLogic;
    }

    public AchievementsLogic getAchievementsLogic() {
        return achievementsLogic;
    }

    public DiaryBookLogic getDiaryLogic() {
        return this.diaryLogic;
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
