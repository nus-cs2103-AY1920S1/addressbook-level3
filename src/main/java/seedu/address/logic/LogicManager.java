package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ElisaState;
import seedu.address.model.ElisaStateHistory;
import seedu.address.model.ElisaStateManager;
import seedu.address.model.ItemModel;
import seedu.address.model.ItemModelManager;
import seedu.address.model.ItemStorage;
import seedu.address.model.item.VisualizeList;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final ItemModel model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final ElisaStateHistory elisaStateHistory;

    public LogicManager(ItemModel model, Storage storage, ElisaStateHistory elisaStateHistory) {
        this.storage = storage;
        this.model = model;
        addressBookParser = new AddressBookParser();
        this.elisaStateHistory = elisaStateHistory;
        elisaStateHistory.pushCommand(new ElisaStateManager(model.getItemStorage(), model.getVisualList()).deepCopy());
    }

    @Override
    public ElisaStateHistory getElisaStateHistory() {
        return elisaStateHistory;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        //Logging
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveItemStorage(model.getItemStorage());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ItemStorage getItemStorage() {
        return model.getItemStorage();
    }

    /*
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getVisualList();
<<<<<<< HEAD
    }

     */
    @Override
    public VisualizeList getVisualList() {
        return model.getVisualList();
    }

    @Override
    public void setState(ElisaStateManager state) {
        this.model.setItemStorage(state.getStorage().deepCopy());
        this.model.setVisualizeList(state.getVisualizeList().deepCopy());
    }

    @Override
    public void updateModelLists() {
        ItemStorage itemStorage = this.model.getItemStorage().deepCopy();
        ItemModelManager tempModel = ((ItemModelManager) this.model);
        this.model.emptyLists();
        for (int i = 0; i < itemStorage.size(); i++) {
            tempModel.addToSeparateList(itemStorage.get(i));
        }
    }

    @Override
    public ElisaState getState() {
        return new ElisaStateManager(model.getItemStorage().deepCopy(), model.getVisualList()).deepCopy();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getItemStorageFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
