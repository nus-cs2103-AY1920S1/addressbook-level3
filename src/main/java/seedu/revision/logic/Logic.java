package seedu.revision.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;

import seedu.revision.commons.core.GuiSettings;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.quiz.Statistics;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Returns the History.
     *
     * @see seedu.revision.model.Model#getHistory()
     */
    ReadOnlyHistory getHistory();

    /** Updates history of quiz statistics with the latest results */
    void updateHistory(Statistics newResult);

    /** Returns an unmodifiable view of the filtered list of answerables */
    ObservableList<Answerable> getFilteredAnswerableList();

    /** Returns an unmodifiable view of the list of statistics */
    ObservableList<Statistics> getStatisticsList();

    /** Returns an unmodifiable view of the filtered and sorted list of answerables */
    ObservableList<Answerable> getFilteredSortedAnswerableList();

    /** Removes the filters from answerable list and restores the list to its original size. **/
    void removeFiltersFromAnswerableList();

    /**
     * Returns the user prefs' revision tool file path.
     */
    Path getRevisionToolFilePath();

    /**
     * Returns the user prefs' revision tool file path.
     */
    Path getHistoryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Executes commands while the Quiz session in operation. Takes in user input and determines command to execute.
     * @param commandText The command as entered by the user.
     * @param currentAnswerable The current question to be responded to.
     * @return commandResult to be executed.
     * @throws ParseException
     * @throws CommandException
     */
    CommandResult execute(String commandText, Answerable currentAnswerable) throws CommandException, ParseException;
}
