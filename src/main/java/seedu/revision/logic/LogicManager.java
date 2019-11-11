package seedu.revision.logic;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.main.ParserManager;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.quiz.Statistics;
import seedu.revision.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ParserManager parserManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        parserManager = new ParserManager();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = parserManager.parseCommand(commandText);
        //Executes the Command and stores the result
        commandResult = command.execute(model);
        try {
            //We can deduce that the previous line of code modifies model in some way
            //since it's being stored here.
            storage.saveRevisionTool(model.getRevisionTool());
            storage.saveHistory(model.getHistory());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }


    @Override
    public CommandResult execute(String commandText, Answerable currentAnswerable)
            throws ParseException, CommandException {
        //Parse user input from String to a Command
        Command command = parserManager.parseCommand(commandText, currentAnswerable);
        //Executes the Command and stores the result
        CommandResult commandResult = command.execute(model);
        if (commandResult.getIsCorrect()) {
            logger.info("Correct answer selected");
        } else {
            logger.info("Wrong answer selected");
        }
        return commandResult;
    }

    @Override
    public ReadOnlyHistory getHistory() {
        return model.getHistory();
    }

    /**
     * Updates the history of quiz statistics.
     * @param newResult model is updated with new quiz statistics.
     */
    @Override
    public void updateHistory(Statistics newResult) {
        requireNonNull(newResult);
        model.addStatistics(newResult);
    }

    @Override
    public ObservableList<Answerable> getFilteredAnswerableList() {
        return model.getFilteredAnswerableList();
    }

    @Override
    public ObservableList<Statistics> getStatisticsList() {
        return model.getStatisticsList();
    }

    public ObservableList<Answerable> getFilteredSortedAnswerableList() {
        return model.getFilteredAnswerableList().sorted(Comparator.comparing(a -> a.getDifficulty()));
    }

    @Override
    public void removeFiltersFromAnswerableList() {
        model.removeFiltersFromAnswerableList();
    }

    @Override
    public Path getRevisionToolFilePath() {
        return model.getRevisionToolFilePath();
    }

    @Override
    public Path getHistoryFilePath() {
        return model.getHistoryFilePath();
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
