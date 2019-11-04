package seedu.tarence.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.DisplayFormat;
import seedu.tarence.logic.commands.TabNames;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.ApplicationParser;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ApplicationParser applicationParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        applicationParser = new ApplicationParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        // save raw input to command history
        model.saveInput(commandText);

        CommandResult commandResult;
        Command command;
        Optional<Tutorial> tutorialToStore = Optional.empty();
        Optional<TabNames> tabToDisplay = Optional.empty();
        Optional<Assignment> assignmentToDisplay = Optional.empty();
        Optional<Map<Student, Integer>> studentsScoreToDisplay = Optional.empty();
        Optional<DisplayFormat> displayFormat = Optional.empty();
        Optional<List<Assignment>> assignmentsToDisplay = Optional.empty();

        // processes multiple commands in user input if they exit
        String[] commandStrings = commandText.split("\\+");
        // pushes commands from back to front on top of the pending commands stack
        for (int i = commandStrings.length - 1; i >= 0; i--) {
            Command tempCommand = applicationParser.parseCommand(commandStrings[i]);
            model.storePendingCommand(tempCommand);
        }

        StringBuffer combinedFeedback = new StringBuffer();
        // clears log of pending commands until it meets a command that requires further user input
        while (model.hasPendingCommand() && !model.peekPendingCommand().needsInput()) {
            command = model.getPendingCommand(); // first user-inputted command

            // if next command requires user input, checks if current command is relevant
            if (model.hasPendingCommand()
                    && model.peekPendingCommand().needsInput()
                    && !model.peekPendingCommand().needsCommand(command)) {
                model.getPendingCommand(); // clear any pending commands if user has entered a different command
            }

            // Undo command needs to be handled separately as involves calling Storage object.
            CommandResult currCommandResult = null;
            if (isUndoCommand(command)) {
                currCommandResult = command.execute(model, storage);
            } else {
                currCommandResult = command.execute(model);
            }

            // concatenate all results into a single result
            combinedFeedback.append(currCommandResult.getFeedbackToUser() + "\n");

            // check for exit/help condition
            if (currCommandResult.isExit() || currCommandResult.isShowHelp()) {
                // this means that previous commands won't be shown if help is inside pending commands
                // but will be executed
                return currCommandResult;
            }

            try {
                storage.saveApplication(model.getApplication());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }

            // If attendance is to be displayed, it will be passed into the commandResult
            if (currCommandResult.isShowAttendance()) {
                tutorialToStore = Optional.of(currCommandResult.getTutorialAttendance());
            }

            // If tab is to be displayed, it will be passed into the commandResult
            if (currCommandResult.isChangeTabs()) {
                tabToDisplay = Optional.of(currCommandResult.getTabToDisplay());
            }

            // If assignment is to be displayed, it will be passed into the commandResult
            if (currCommandResult.isAssignmentDisplay()) {
                assignmentToDisplay = Optional.of(currCommandResult.getAssignmentToDisplay());
                studentsScoreToDisplay = Optional.of(currCommandResult.getStudentScores());
                displayFormat = Optional.of(currCommandResult.getAssignmentDisplayFormat());
            }

            if (currCommandResult.isAssignmentsDisplay()) {
                logger.info("----------------[COMMAND RESULT][Displaying Assignments]");
                assignmentsToDisplay = Optional.of(currCommandResult.getAssignmentsToDisplay());
            }
        }

        // creates a new command concatenating all command result messages into a single result
        if (tutorialToStore.isPresent()) {
            commandResult = new CommandResult(combinedFeedback.toString(), tutorialToStore.get());
        } else if (tabToDisplay.isPresent()) {
            commandResult = new CommandResult(combinedFeedback.toString(), tabToDisplay.get());
        } else if (displayFormat.isPresent() && assignmentToDisplay.isPresent() && studentsScoreToDisplay.isPresent()) {
            commandResult = new CommandResult(combinedFeedback.toString(), assignmentToDisplay.get(),
                    studentsScoreToDisplay.get(), displayFormat.get());
        } else if (assignmentsToDisplay.isPresent()) {
            commandResult = new CommandResult(combinedFeedback.toString(), assignmentsToDisplay.get());
        } else {
            commandResult = new CommandResult(combinedFeedback.toString());
        }

        return commandResult;
    }

    /**
     * Checks if an undo command is created. Can be expanded to include other commands that involves the Storage object.
     * @param command Command parsed from user String.
     * @return True if command is of type "undo"
     */
    public Boolean isUndoCommand(Command command) {
        return command.toString().toLowerCase().contains("undo");
    }

    @Override
    public String autocomplete(String partialInput) throws ParseException, IndexOutOfBoundsException {
        return new AutocompleteHandler(model).handle(partialInput);
    }

    @Override
    public String getNextSuggestion() throws ParseException {
        return new AutocompleteHandler(model).getNextSuggestion();
    }

    @Override
    public String getPastInput(String arrowDirection) {
        return new InputHistoryHandler(model).getPastInput(arrowDirection);
    }

    @Override
    public void markInputChanged() {
        model.setInputChangedToTrue();
    }

    @Override
    public ReadOnlyApplication getApplication() {
        return model.getApplication();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return model.getFilteredTutorialList();
    }

    @Override
    public Path getApplicationFilePath() {
        return model.getApplicationFilePath();
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
