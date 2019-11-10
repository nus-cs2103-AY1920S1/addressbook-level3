package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;

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
     * Returns the Catalog.
     *
     * @see seedu.address.model.Model#getCatalog()
     */
    ReadOnlyCatalog getCatalog();

    /** Returns an unmodifiable view of the filtered list of books */
    ObservableList<Book> getFilteredBookList();

    /** Returns true if model within Logic component is in Serve Mode */
    boolean isServeMode();

    /** Returns the borrower being served in Serve Mode, or null if in Normal Mode */
    Borrower getServingBorrower();

    /** Returns the list of books borrowed by the borrower being served */
    ObservableList<Book> getServingBorrowerBookList();

    String getLoanHistoryOfBookAsString(Book target);

    /**
     * Returns the user prefs' catalog file path.
     */
    Path getCatalogFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Get the initial load status message on start up
     */
    String getLoadStatus();
}
