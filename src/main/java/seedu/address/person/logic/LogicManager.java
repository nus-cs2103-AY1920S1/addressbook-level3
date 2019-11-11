package seedu.address.person.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.person.commons.core.GuiSettings;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.logic.commands.Command;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.logic.parser.AddressBookParser;
import seedu.address.person.logic.parser.exceptions.ParseException;
import seedu.address.person.model.Model;
import seedu.address.person.model.ReadOnlyAddressBook;
import seedu.address.person.model.person.Person;
import seedu.address.person.storage.Storage;
import seedu.address.util.CommandResult;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final seedu.address.transaction.logic.Logic transactionLogic;
    private final seedu.address.reimbursement.logic.Logic reimbursementLogic;

    public LogicManager(Model model, Storage storage, seedu.address.transaction.logic.Logic transactionLogic,
                        seedu.address.reimbursement.logic.Logic reimbursementLogic) {
        this.model = model;
        this.storage = storage;
        this.transactionLogic = transactionLogic;
        this.reimbursementLogic = reimbursementLogic;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, IOException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model, transactionLogic, reimbursementLogic);

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
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
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
