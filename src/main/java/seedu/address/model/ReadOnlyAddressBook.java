package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.claim.Claim;
import seedu.address.model.income.Income;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the income list.
     * This list will not contain any duplicate incomes.
     */
    ObservableList<Income> getIncomeList();

    /**
     * Returns an unmodifiable view of the claims list.
     * This list will not contain any duplicate claims.
     */
    ObservableList<Claim> getClaimList();
}
