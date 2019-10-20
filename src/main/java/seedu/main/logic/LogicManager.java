package seedu.main.logic;

import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.AddressBookLogic;
import seedu.address.logic.AddressBookLogicManager;
import seedu.address.storage.Storage;
import seedu.main.model.Model;

/**
 * The main AddressBookLogicManager of the app.
 */
public class LogicManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private AddressBookLogic addressBookLogic;
    private MainLogic mainLogic;
    private Storage storage;

    public LogicManager(Model model, Storage storage) {
        this.addressBookLogic = new AddressBookLogicManager(model.getMainModel(), model.getAddressBookModel(), storage);
        this.mainLogic = new MainLogicManager(model.getMainModel(), storage);
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    public AddressBookLogic getAddressBookLogic() {
        return addressBookLogic;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return mainLogic.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        mainLogic.setGuiSettings(guiSettings);
    }
}
