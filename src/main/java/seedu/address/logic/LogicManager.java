package seedu.address.logic;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
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
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final IncidentManagerParser incidentManagerParser;

    public LogicManager(Model model, Storage storage) {
        requireAllNonNull(model, storage);
        this.model = model;
        this.storage = storage;
        incidentManagerParser = new IncidentManagerParser(true, false);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        incidentManagerParser.setLoggedIn(model.isLoggedIn());
        CommandResult commandResult;
        Command command = incidentManagerParser.parseCommand(commandText);

        commandResult = command.execute(model);

        try {
            assert storage != null;
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
        return incidentManagerParser.isPersonView();
    }

    @Override
    public void setPersonView(boolean isPersonView) {
        incidentManagerParser.setPersonView(isPersonView);
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
