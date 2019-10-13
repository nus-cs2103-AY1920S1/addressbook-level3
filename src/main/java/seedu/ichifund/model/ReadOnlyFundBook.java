package seedu.ichifund.model;

import javafx.collections.ObservableList;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.person.Person;

/**
 * Unmodifiable view of an fund book
 */
public interface ReadOnlyFundBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the budgets list.
     * This list will not contain any duplicate budgets.
     */
    ObservableList<Budget> getBudgetList();

}
