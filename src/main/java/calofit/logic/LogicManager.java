package calofit.logic;

import calofit.logic.commands.Command;
import calofit.logic.commands.CommandResult;
import calofit.logic.commands.exceptions.CommandException;
import calofit.logic.parser.AddressBookParser;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.meal.Meal;
import javafx.collections.ObservableList;
import calofit.commons.core.GuiSettings;
import calofit.commons.core.LogsCenter;
import calofit.model.Model;
import calofit.model.ReadOnlyAddressBook;
import calofit.storage.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Meal> getFilteredMealList() {
        return model.getFilteredMealList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
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
