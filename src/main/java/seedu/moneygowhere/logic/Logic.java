package seedu.moneygowhere.logic;

import java.nio.file.Path;
import java.util.LinkedHashMap;

import javafx.collections.ObservableList;
import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.spending.Spending;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the SpendingBook.
     *
     * @see seedu.moneygowhere.model.Model#getSpendingBook()
     */
    ReadOnlySpendingBook getSpendingBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Spending> getFilteredSpendingList();

    /**
     * Returns the user prefs' MoneyGoWhere file path.
     */
    Path getSpendingBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns a map of spending with key and value pair representing data for the statistics chart.
     */
    LinkedHashMap<String, Double> getStatsData();

    /**
     * Returns a map of spending with key and value pair representing data for the graph.
     */
    LinkedHashMap<String, Double> getGraphData();

    /**
     * Returns the previous user inputted command with respect to the current index.
     * @return The previous user inputted command.
     */
    String getPrevCommand();

    /**
     * Returns the next user inputted command with respect to the current index.
     * @return The next user inputted command.
     */
    String getNextCommand();
}
