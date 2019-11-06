package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TutorAidParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTutorAid;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.note.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TutorAidParser tutorAidParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        tutorAidParser = new TutorAidParser(getFilteredCommandsList());

    }

    @Override
    public CommandResult execute(String commandText, boolean isUnknown) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        try {
            if (model.userHasLoggedIn()) {
                Command command;

                if (isUnknown) {
                    command = tutorAidParser.checkCommand(commandText, model.getSavedCommand());
                } else {
                    command = tutorAidParser.parseCommand(commandText);
                }
                commandResult = command.execute(model);
                storage.saveTutorAid(model.getTutorAid());

            } else {
                Command command = tutorAidParser.parseCommandWithoutLoggingIn(commandText);
                commandResult = command.execute(model);
            }

            return commandResult;
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

    }

    @Override
    public ReadOnlyTutorAid getTutorAid() {
        return model.getTutorAid();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Earnings> getFilteredEarningsList() {
        return model.getFilteredEarningsList();
    }

    @Override
    public ObservableList<CommandObject> getFilteredCommandsList() {
        return model.getFilteredCommandsList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return model.getFilteredReminderList();
    }

    @Override
    public ObservableList<Notes> getFilteredNotesList() {
        return model.getFilteredNotesList();
    }

    @Override
    public Path getTutorAidFilePath() {
        return model.getTutorAidFilePath();
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
