package seedu.moolah.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.ReadOnlyMooLah;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.statistics.Statistics;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @param commandGroup
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText, String commandGroup) throws CommandException, ParseException;

    void save() throws CommandException;

    boolean hasBudgetWithName(Description targetDescription);

    Statistics getStatistics();


    /**
     * Returns the MooLah.
     *
     * @see seedu.moolah.model.Model#getMooLah()
     */
    ReadOnlyMooLah getMooLah();

    /** Returns an unmodifiable view of the filtered list of expenses */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<Event> getFilteredEventList();

    /** Returns an unmodifiable view of the filtered list of budgets */
    ObservableList<Budget> getFilteredBudgetList();

    Budget getPrimaryBudget();

    /**
     * Returns the user prefs' MooLah file path.
     */
    Path getMooLahFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    AliasMappings getAliasMappings();

    boolean deleteAliasWithName(String aliasName);

    void deleteTranspiredEvent(Event eventToBeRemoved);

    CommandResult addExpenseFromEvent(Event currentEvent) throws CommandException, ParseException;

    boolean[] recordInitialPrimaryBudgetStatus();

    boolean[] recordFinalPrimaryBudgetStatus();


}
