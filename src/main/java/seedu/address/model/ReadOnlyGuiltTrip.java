package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.entry.AutoExpense;
import seedu.address.model.entry.Budget;
import seedu.address.model.entry.Category;
import seedu.address.model.entry.CategoryList;
import seedu.address.model.entry.Entry;
import seedu.address.model.entry.Expense;
import seedu.address.model.entry.Income;
import seedu.address.model.entry.Wish;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.conditions.Condition;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyGuiltTrip extends Observable {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain
     * any duplicate persons.
     */
    CategoryList getCategoryList();

    ObservableList<Category> getExpenseCategoryList();

    ObservableList<Category> getIncomeCategoryList();

    ObservableList<Entry> getEntryList();

    ObservableList<Expense> getExpenseList();

    ObservableList<Income> getIncomeList();

    ObservableList<Wish> getWishList();

    ObservableList<Budget> getBudgetList();

    ObservableList<AutoExpense> getAutoExpenseList();

    ObservableList<Reminder> getReminderList();

    ObservableList<Condition> getConditionList();
}
