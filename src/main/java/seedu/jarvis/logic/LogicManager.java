package seedu.jarvis.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.JarvisParser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.ReadOnlyAddressBook;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final AddressModel addressModel;
    private final Storage storage;
    private final JarvisParser jarvisParser;

    public LogicManager(AddressModel addressModel, Storage storage) {
        this.addressModel = addressModel;
        this.storage = storage;
        jarvisParser = new JarvisParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = jarvisParser.parseCommand(commandText);
        commandResult = command.execute(addressModel);

        try {
            storage.saveAddressBook(addressModel.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressModel.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return addressModel.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return addressModel.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return addressModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        addressModel.setGuiSettings(guiSettings);
    }
}
