package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the user prefs' loan records file path.
     */
    Path getLoanRecordsFilePath();

    /**
     * Sets the user prefs' loan records file path.
     */
    void setLoanRecordsFilePath(Path loanRecordsFilePath);

    /**
     * Returns the user prefs' catalog file path.
     */
    public Path getCatalogFilePath();

    /**
     * Returns the user prefs' catalog file path.
     */
    public void setCatalogFilePath(Path catalogFilePath);

    /**
     * Returns the user prefs' borrower records file path.
     */
    Path getBorrowerRecordsFilePath();

    /**
     * Sets the user prefs' address borrower records path.
     */
    void setBorrowerRecordsFilePath(Path borrowerRecordsFilePath);


    /** Returns the LoanRecords*/
    ReadOnlyLoanRecords getLoanRecords();

    /** Returns the Catalog*/
    ReadOnlyCatalog getCatalog();

    /** Returns the BorrowerRecords*/
    ReadOnlyBorrowerRecords getBorrowerRecords();
}
