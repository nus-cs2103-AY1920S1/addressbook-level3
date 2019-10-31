package seedu.scheduler.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.scheduler.commons.core.GuiSettings;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.logic.commands.Command;
import seedu.scheduler.logic.commands.CommandResult;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.AddressBookParser;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ReadOnlyList;
import seedu.scheduler.model.Schedule;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.storage.Storage;

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

    /* TODO: REMOVE THE FOLLOWING LINES AFTER THEIR USAGE IS REMOVED */
    public Path getAddressBookFilePath() {
        return this.model.getIntervieweeListFilePath();
    }

    /* TODO: REMOVE ABOVE LINES */

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveIntervieweeList(model.getMutableIntervieweeList());
            storage.saveInterviewerList(model.getMutableInterviewerList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    // ==================================IntervieweeList and InterviewerList ======================================

    @Override
    public ReadOnlyList<Interviewee> getIntervieweeList() {
        return model.getMutableIntervieweeList();
    }

    @Override
    public ReadOnlyList<Interviewer> getInterviewerList() {
        return model.getMutableInterviewerList();
    }

    @Override
    public ObservableList<Interviewee> getUnfilteredIntervieweeList() {
        return model.getUnfilteredIntervieweeList();
    }

    @Override
    public ObservableList<Interviewer> getUnfilteredInterviewerList() {
        return model.getUnfilteredInterviewerList();
    }

    @Override
    public ObservableList<Interviewee> getFilteredIntervieweeList() {
        return model.getFilteredIntervieweeList();
    }

    @Override
    public ObservableList<Interviewer> getFilteredInterviewerList() {
        return model.getFilteredInterviewerList();
    }

    @Override
    public Path getIntervieweeListFilePath() {
        return model.getIntervieweeListFilePath();
    }

    @Override
    public Path getInterviewerListFilePath() {
        return model.getInterviewerListFilePath();
    }

    // ============================================ Schedule ===================================================

    @Override
    public List<Schedule> getSchedulesList() {
        return model.getSchedulesList();
    }

    @Override
    public List<ObservableList<ObservableList<String>>> getObservableLists() {
        return model.getObservableLists();
    }

    @Override
    public List<List<String>> getTitlesLists() {
        return model.getTitlesLists();
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
