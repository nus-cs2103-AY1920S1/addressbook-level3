package seedu.tarence.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.ApplicationParser;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
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

        CommandResult commandResult;
        Command command;

        // processes multiple commands in user input if they exit
        String[] commandStrings = commandText.split("&");
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

            CommandResult currCommandResult = command.execute(model);

            // concatenate all results into a single result
            combinedFeedback.append(currCommandResult.getFeedbackToUser() + "\n");

            // check for exit/help condition
            if (currCommandResult.isExit() || currCommandResult.isShowHelp()) {
                // this means that previous commands won't be shown if help is inside pending commands
                return currCommandResult;
            }

            try {
                storage.saveApplication(model.getApplication());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        // creates a new command concatenating all command result messages into a single result
        commandResult = new CommandResult(combinedFeedback.toString());

        return commandResult;
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
