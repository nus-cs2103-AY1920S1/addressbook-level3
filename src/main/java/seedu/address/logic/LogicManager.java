package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AthletickParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAthletick;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.performance.Event;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AthletickParser athletickParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        athletickParser = new AthletickParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = athletickParser.parseCommand(commandText);
        HistoryManager history = model.getHistory();
        commandResult = command.execute(model);
        if (model.commandUnderTraining(command) || command instanceof EditCommand) {
            history.pushTrainingList(model.getTrainingsDeepCopy(model.getTrainingManager().getTrainings()));
            history.pushPerformances(model.getPerformanceDeepCopy(model.getPerformance()));
        }
        if (model.commandUnderPerformance(command)) {
            history.pushPerformances(model.getPerformanceDeepCopy(model.getPerformance()));
        }
        if (command instanceof ClearCommand) {
            history.pushPerformances(model.getPerformanceDeepCopy(model.getPerformance()));
            history.pushTrainingList(model.getTrainingsDeepCopy(model.getTrainingManager().getTrainings()));
        }
        history.pushCommand(command);
        history.pushAthletick(model.getAthletickDeepCopy());
        try {
            storage.saveAthletick(model.getAthletick());
            storage.saveEvents(model.getPerformance());
            storage.saveTrainingManager(model.getTrainingManager());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
        return commandResult;
    }

    @Override
    public ReadOnlyAthletick getAthletick() {
        return model.getAthletick();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Person getPerson() {
        return model.selectPerson();
    }

    @Override
    public String getPersonAttendance() {
        return model.getTrainingManager().getPersonAttendanceRateString(getPerson());
    }

    @Override
    public ArrayList<Event> getAthleteEvents() {
        return model.getAthleteEvents(getPerson());
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAthletickFilePath();
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
