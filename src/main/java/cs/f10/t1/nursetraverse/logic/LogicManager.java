package cs.f10.t1.nursetraverse.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;
import cs.f10.t1.nursetraverse.commons.core.LogsCenter;
import cs.f10.t1.nursetraverse.logic.commands.Command;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.logic.parser.AppParser;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.storage.Storage;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String STAGED_BUT_NOT_MUTATOR_ERROR_MESSAGE = "Command attempted to make changes but is not a "
            + "MutatorCommand";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AppParser appParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        appParser = new AppParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = appParser.parseCommand(commandText);
        commandResult = command.execute(model);

        if (command instanceof MutatorCommand) {
            if (!model.hasStagedChanges()) {
                logger.info("Command " + command + " is a MutatorCommand but did not stage changes");
            }
            model.commit((MutatorCommand) command);
        } else {
            if (model.hasStagedChanges()) {
                model.discardStagedChanges();
                logger.severe("Command " + command + " staged changes but is not a MutatorCommand."
                        + " Changes discarded.");
                throw new CommandException(STAGED_BUT_NOT_MUTATOR_ERROR_MESSAGE);
            }
        }

        try {
            storage.savePatientBook(model.getStagedPatientBook());
            storage.saveAppointmentBook(model.getStagedAppointmentBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPatientBook getPatientBook() {
        return model.getStagedPatientBook();
    }

    @Override
    public FilteredList<Patient> getFilteredPatientList() {
        return model.getFilteredPatientList();
    }

    @Override
    public FilteredList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }

    @Override
    public Path getPatientBookFilePath() {
        return model.getPatientBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Visit> getObservableOngoingVisitList() {
        return model.getObservableOngoingVisitList();
    }

    @Override
    public ObservableList<HistoryRecord> getObservableHistoryList() {
        return model.getHistory();
    }
}
