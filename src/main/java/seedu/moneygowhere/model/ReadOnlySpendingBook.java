package seedu.moneygowhere.model;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.currency.Currency;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Unmodifiable view of the MoneyGoWhere list
 */
public interface ReadOnlySpendingBook {

    /**
     * Returns an unmodifiable view of the spending list.
     * This list will not contain any duplicate spending.
     */
    ObservableList<Spending> getSpendingList();

    /**
     * Returns the Budget of the MoneyGoWhere list.
     */
    Budget getBudget();

    /**
     * Returns an unmodifiable view of the reminder list.
     */
    ObservableList<Reminder> getReminderList();

    /**
     * Returns available currencies.
     */
    ObservableList<Currency> getCurrencies();

    /**
     * Returns the currency being used.
     */
    Currency getCurrencyInUse();

    /**
     * Registers a currency changed listener.
     * @param currencyChangeListener Currency changed listener
     */
    void registerCurrencyChangedListener(ChangeListener<Currency> currencyChangeListener);
}
