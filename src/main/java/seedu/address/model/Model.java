package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Toggles between Light and Dark Theme
     */
    void toggleGuiSettingsTheme();

    /**
     * Returns the user prefs' User settings.
     */
    UserSettings getUserSettings();

    /**
     * Sets the user prefs' User settings.
     */
    void setUserSettings(UserSettings userSettings);

    // ================================================================ Catalog

    public Path getCatalogFilePath();

    /**
     * Returns the user prefs' catalog file path.
     */
    public void setCatalogFilePath(Path catalogFilePath);

    /** Returns the Catalog */
    ReadOnlyCatalog getCatalog();

    /**
     * Returns the user prefs' loan records file path.
     */
    void setCatalog(ReadOnlyCatalog catalog);

    /**
     * Returns true if a book with the same identity as {@code book} exists in the catalog.
     */
    boolean hasBook(Book book);

    /**
     * Returns true if a book with the same serial number as {@code bookSn} exists in the catalog.
     */
    boolean hasBook(SerialNumber bookSn);

    /**
     * Returns the user prefs' catalog file path.
     */
    void deleteBook(Book target);

    void addBook(Book book);

    Book getBook(SerialNumber bookSn);

    void setBook(Book target, Book editedBook);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Book> getFilteredBookList();

    void resetFilteredBookList();

    void updateFilteredBookList(Predicate<Book> predicate);

    Model excludeBookBeingReplaced(Book toBeReplaced);

    ObservableList<Book> getOverdueBooks();

    String getLoanHistoryOfBookAsString(Book target);

    // ================================================================ LoanRecords

    Path getLoanRecordsFilePath();

    void setLoanRecordsFilePath(Path loanRecordsFilePath);

    /**
     * Returns the LoanRecords.
     */
    ReadOnlyLoanRecords getLoanRecords();

    /**
     * Adds a new Loan to LoanRecords.
     */
    void addLoan(Loan loan);

    /**
     * Removes a Loan from LoanRecords.
     */
    void removeLoan(Loan loan);

    void updateLoan(Loan existingLoan, Loan returnedLoan);

    int payFines(int amountInCents);

    // ================================================================ BorrowerRecords

    /**
     * Returns the user prefs' borrower records file path.
     */
    Path getBorrowerRecordsFilePath();

    /**
     * Sets the user prefs' address borrower records path.
     */
    void setBorrowerRecordsFilePath(Path borrowerRecordsFilePath);

    /** Returns the BorrowerRecords*/
    ReadOnlyBorrowerRecords getBorrowerRecords();

    /**
     * If in serve mode, returns borrower that is being served.
     * Else, returns empty optional.
     *
     * @return Borrower that is being Served.
     */
    Borrower getServingBorrower();

    /**
     * Returns true if Liberry is currently in Serve mode, false otherwise.
     *
     * @return True if Liberry is currently in Serve mode.
     */
    boolean isServeMode();

    boolean hasBorrower(Borrower borrower);

    void registerBorrower(Borrower borrower);

    void resetGenerator();

    void setServingBorrower(BorrowerId borrowerId);

    void setServingBorrower(Borrower borrower);

    void servingBorrowerNewLoan(Loan newLoan);

    void servingBorrowerRemoveLoan(Loan removeLoan);

    void servingBorrowerReturnLoan(Loan loanToBeReturned, Loan returnedLoan);

    void servingBorrowerUnreturnLoan(Loan loanToBeUnreturned, Loan unturnedLoan);

    void servingBorrowerRenewLoan(Loan loanToBeRenewed, Loan renewedLoan);

    void servingBorrowerUnrenewLoan(Loan loanToBeUnrenewed, Loan unrenewLoan);

    boolean hasBorrowerId(BorrowerId borrowerId);

    void exitsServeMode();

    List<Book> getBorrowerBooks();

    Borrower getBorrowerFromId(BorrowerId borrowerId);

    void setBorrower(Borrower borrowerToEdit, Borrower editedBorrower);

    boolean hasDuplicatedBorrower(Borrower editedBorrower);

    void unregisterBorrower(Borrower toUnregister);

    //=========== CommandHistory ===============================================================================

    /**
     * Checks whether there is {@code Command} to undo.
     *
     * @return true if there is {@code Command} to undo, else false.
     */
    boolean canUndoCommand();

    /**
     * Checks whether there is {@code Command} to redo.
     *
     * @return true if there is {@code Command} to redo, else false.
     */
    boolean canRedoCommand();

    /**
     * Commits {@code ReversibleCommand} to the {@code CommandHistory}.
     */
    void commitCommand(ReversibleCommand command);

    /**
     * Undoes the most recent committed {@code ReversibleCommand}.
     */
    Pair<CommandResult, CommandResult> undoCommand() throws CommandException;

    /**
     * Redoes the most recent undone {@code ReversibleCommand}.
     */
    CommandResult redoCommand() throws CommandException;

    /**
     * Resets the command history.
     */
    void resetCommandHistory();

    /**
     * gets the initial load status message on start up
     */
    String getLoadStatus();
}
