package seedu.main.logic;

import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.Storage;
import seedu.main.model.MainModel;

/**
 * The main AddressBookLogicManager of the app.
 */
public class MainLogicManager implements MainLogic {
    private final Logger logger = LogsCenter.getLogger(MainLogicManager.class);

    private MainModel mainModel;
    private Storage storage;

    public MainLogicManager(MainModel mainModel, Storage storage) {
        this.mainModel = mainModel;
        this.storage = storage;
    }


    @Override
    public GuiSettings getGuiSettings() {
        return mainModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        mainModel.setGuiSettings(guiSettings);
    }
}
