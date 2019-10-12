package seedu.billboard.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.model.expense.Expense;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

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

    // ================ Billboard methods ==============================

    /**
     * Returns the user prefs' address book file path.
     */
    Path getBillboardFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setBillboardFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code billboard}.
     */
    void setBillboardExpenses(ReadOnlyBillboard billboardExpenses);

    /** Returns the Billboard */
    ReadOnlyBillboard getBillboardExpenses();

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the address book.
     */
    boolean hasExpense(Expense expense);

    /**
     * Deletes the given expense.
     * The expense must exist in the address book.
     */
    void deleteExpense(Expense target);

    /**
     * Adds the given expense.
     * {@code expense} must not already exist in the address book.
     */
    void addExpense(Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the address book.
     * The expense {@code editedExpense} must not be the same as another existing expense in the address book.
     */
    void setExpense(Expense target, Expense editedExpense);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Expense> getFilteredExpenses();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenses(Predicate<Expense> predicate);

    // ================ Archive methods ==============================

    /**
     * Returns the user prefs' archive file path.
     */
    Path getArchiveFilePath();

    /**
     * Sets the user prefs' archive file path.
     */
    void setArchiveFilePath(Path archiveFilePath);

    /**
     * Replaces archive data with the data in {@code billboard}.
     */
    void setArchiveExpenses(ReadOnlyArchives archiveExpenses);

    /** Returns the archive */
    ReadOnlyArchives getArchiveExpenses();

    /**
     * Returns true if an expense with the same identity as {@code expense} exists in the given archive.
     */
    boolean hasArchiveExpense(String archiveName, Expense expense);

    /**
     * Deletes the given expense in the given archive.
     * The given {@code archiveName} must exist.
     */
    void deleteArchiveExpense(String archiveName, Expense target);

    /**
     * Adds the given expense into the given archive.
     * The given {@code archiveName} must exist.
     */
    void addArchiveExpense(String archiveName, Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense} in the given archive.
     * The given {@code archiveName} must exist.
     * {@code target} must exist in the archive.
     */
    void setArchiveExpense(String archiveName, Expense target, Expense editedExpense);

    /** Returns an unmodifiable view of the filtered archive expense list */
    ObservableList<Expense> getFilteredArchiveExpenses(String archiveName);

    /**
     * Updates the filter of the filtered archive expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredArchiveExpenses(String archiveName, Predicate<Expense> predicate);
}
