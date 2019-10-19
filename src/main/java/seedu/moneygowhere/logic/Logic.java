package seedu.moneygowhere.logic;

import java.nio.file.Path;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.spending.Date;
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
     * Executes the command and returns the graph data.
     * @param commandText The command as entered by the user.
     * @return the hashmap of spending data
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    Map<Date, Double> getGraphData(String commandText) throws ParseException;

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
}
