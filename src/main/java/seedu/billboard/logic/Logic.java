package seedu.billboard.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.logic.commands.CommandResult;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.ReadOnlyArchiveWrapper;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.StatisticsFormat;
import seedu.billboard.model.statistics.formats.StatisticsFormatOptions;

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
     * Returns the Billboard.
     *
     * @see seedu.billboard.model.Model#getBillboard()
     */
    ReadOnlyBillboard getBillboard();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Expense> getFilteredExpenseList();

    /**
     * Returns an observable view of the selected statistic type.
     */
    ObservableData<StatisticsFormat> getStatisticsFormat();

    /**
     * Returns the statistics format options wrapped in an observable wrapper.
     */
    ObservableData<StatisticsFormatOptions> getStatisticsFormatOptions();

    /**
     * Returns the user prefs' billboard file path.
     */
    Path getBillboardFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the archive.
     *
     * @see seedu.billboard.model.Model#getArchives()
     */
    ReadOnlyArchiveWrapper getArchive();

    /** Returns an unmodifiable view of the filtered list of archive expenses */
    ObservableList<Expense> getFilteredArchiveExpenseList(String archiveName);
}
