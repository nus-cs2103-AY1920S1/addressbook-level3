package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;

/**
 * Unmodifiable view of Contact
 */
public interface ReadOnlyIncome {

    /**
     * Returns an unmodifiable view of the incomes list.
     * This list will not contain any duplicate incomes.
     */
    ObservableList<Income> getIncomeList();

}
