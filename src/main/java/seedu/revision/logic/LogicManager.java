package seedu.revision.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.CommandResult;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.parser.AddressBookParser;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.storage.Storage;

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
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = addressBookParser.parseCommand(commandText);
        //Executes the Command and stores the result
        commandResult = command.execute(model);

        try {
            //We can deduce that the previous line of code modifies model in some way
            //since it's being stored here.
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
    public ObservableList<Answerable> getFilteredAnswerableList() {
        return model.getFilteredAnswerableList();
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
