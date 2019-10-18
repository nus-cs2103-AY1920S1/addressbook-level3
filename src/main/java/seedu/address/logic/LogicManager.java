package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.Session;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String ACCESS_CONTROL_MESSAGE = "Only Add and Login commands available.\n"
            + "Please login to access other commands. See help page for more information.";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private boolean isPersonView;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        isPersonView = true;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);

        // Guard Statement for available commands prior to login.
        if (!model.isLoggedIn() && !(command instanceof LoginCommand || command instanceof AddCommand)) {
            throw new CommandException(ACCESS_CONTROL_MESSAGE);
        }
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
    public Session getSession() {
        return model.getSession();
    }

    @Override
    public boolean isPersonView() {
        return isPersonView;
    }

    @Override
    public void isPersonView(boolean isPersonView) {
        this.isPersonView = isPersonView;
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Incident> getFilteredIncidentList() {
        return model.getFilteredIncidentList();
    }

    @Override
    public ObservableList<Vehicle> getFilteredVehicleList() {
        return model.getFilteredVehicleList();
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
