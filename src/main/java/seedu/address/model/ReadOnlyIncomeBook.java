package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.income.Income;

/**
 * Unmodifiable view of an income book
 */
public interface ReadOnlyIncomeBook {

    /**
     * Returns an unmodifiable view of the claims list.
     * This list will not contain any duplicate claims.
     */
    ObservableList<Income> getIncomeList();
}

