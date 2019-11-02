package seedu.address.logic;

import java.util.logging.Logger;

import seedu.address.achievements.logic.AchievementsLogic;
import seedu.address.achievements.logic.AchievementsLogicManager;
import seedu.address.address.logic.AddressBookLogic;
import seedu.address.address.logic.AddressBookLogicManager;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
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
    private Storage storage;

    public LogicManager(Model model, Storage storage) {
        // overloaded AddressBook Logic Manager to pass main model in
        // main model is used to save gui settings
        this.userPrefsModel = model.getUserPrefsModel();
        this.addressBookLogic = new AddressBookLogicManager(userPrefsModel, model.getAddressBookModel(), storage);
        this.achievementsLogic = new AchievementsLogicManager(userPrefsModel, model.statisticsModelSupplier());
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

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefsModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        userPrefsModel.setGuiSettings(guiSettings);
    }
}
