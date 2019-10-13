package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.claim.Claim;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Contact> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Claim> PREDICATE_SHOW_ALL_CLAIMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Income> PREDICATE_SHOW_ALL_INCOMES = unused -> true;

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
    Path getFinSecFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setFinSecFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code finSec}.
     */
    void setFinSec(ReadOnlyFinSec finSec);

    /** Returns the FinSec */
    ReadOnlyFinSec getFinSec();


    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contact.
     * {@code contact} must not already exist in the address book.
     */
    void addContact(Contact contact);


    /**
     * Returns true if a claim with the same identity as {@code claim} exists.
     */
    boolean hasClaim(Claim claim);

    /**
     * Deletes the given claim.
     * The claim must exist.
     */
    void deleteClaim(Claim target);

    /**
     * Adds the given claim.
     * {@code claim} must not already exist.
     */
    void addClaim(Claim claim);

    /**
     * Replaces the given contact {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the address book.
     */
    void setContact(Contact target, Contact editedContact);

    /**
     * Replaces the given claim {@code target} with {@code editedClaim}.
     * {@code target} must exist in the claim list.
     * The claim identity of {@code editedClaim} must not be the same as another existing claim in the claim list.
     */
    void setClaim(Claim target, Claim editedClaim);

    /**
     * Returns true if an income with the same identity as {@code income} exists in the address book.
     */
    boolean hasIncome(Income income);

    /**
     * Deletes the given income.
     * The income must exist in the address book.
     */
    void deleteIncome(Income target);

    /**
     * Adds the given income.
     * {@code income} must not already exist in the address book.
     */
    void addIncome(Income income);

    /**
     * Replaces the given income {@code target} with {@code editedIncome}.
     * {@code target} must exist in the address book.
     * The income identity of {@code editedIncome} must not be the same as another existing income
     * in the address book.
     */
    void setIncome(Income target, Income editedIncome);


    /** Returns an unmodifiable view of the filtered contact list */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Updates the filter of the filtered contact list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate);

    /** Returns an unmodifiable view of the filtered claim list */
    ObservableList<Claim> getFilteredClaimList();

    /**
     * Updates the filter of the filtered claim list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClaimList(Predicate<Claim> predicate);

    /** Returns an unmodifiable view of the filtered income list */
    ObservableList<Income> getFilteredIncomeList();

    /**
     * Updates the filter of the filtered income list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIncomeList(Predicate<Income> predicate);
}
