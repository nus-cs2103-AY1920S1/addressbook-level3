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
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindPersonsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListPersonsCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.SwapCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.IncidentManagerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyIncidentManager;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String ACCESS_CONTROL_MESSAGE = "Only Register, Login, Exit, and Help commands are available.\n"
            + "Please login to access other commands. See help page for more information.";
    public static final String GUI_SWAP_MESSAGE = "Please swap the interface to access the command from this suite.\n"
            + "See help page for more information.";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final IncidentManagerParser incidentManagerParser;
    private boolean isPersonView;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        incidentManagerParser = new IncidentManagerParser();
        isPersonView = true;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = incidentManagerParser.parseCommand(commandText);

        //@@author madanalogy
        // Guard Statement for available commands prior to login.
        if (!model.isLoggedIn() && !(command instanceof LoginCommand || command instanceof AddCommand
                || command instanceof ExitCommand || command instanceof HelpCommand)) {
            throw new CommandException(ACCESS_CONTROL_MESSAGE);
        }

        // Guard Statement for command suite corresponding to interface swaps.
        if (!isPersonView && (command instanceof AddCommand || command instanceof UpdateCommand
                || command instanceof DeleteCommand || command instanceof ListPersonsCommand
                || command instanceof FindPersonsCommand)) {
            throw new CommandException(GUI_SWAP_MESSAGE);
        } else if (isPersonView && !(command instanceof AddCommand || command instanceof UpdateCommand
                || command instanceof DeleteCommand || command instanceof ListPersonsCommand
                || command instanceof FindPersonsCommand || command instanceof LoginCommand
                || command instanceof ExitCommand || command instanceof SwapCommand
                || command instanceof LogoutCommand || command instanceof HelpCommand)) {
            throw new CommandException(GUI_SWAP_MESSAGE);
        }

        commandResult = command.execute(model);

        try {
            assert storage != null;
            assert(model != null);
            assert(model.getIncidentManager() != null);
            storage.saveIncidentManager(model.getIncidentManager());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyIncidentManager getIncidentManager() {
        return model.getIncidentManager();
    }

    @Override
    public Person getLoggedInPerson() {
        return model.getLoggedInPerson();
    }

    @Override
    public String getLoginTime() {
        return model.getLoginTime();
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
    public Path getIncidentManagerFilePath() {
        return model.getIncidentManagerFilePath();
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
