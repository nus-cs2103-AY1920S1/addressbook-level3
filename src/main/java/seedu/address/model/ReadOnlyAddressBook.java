package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.person.AutoExpense;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Category;
import seedu.address.model.person.CategoryList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;
import seedu.address.model.person.Wish;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.conditions.Condition;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain
     * any duplicate persons.
     */
    void addCategory(Category category);

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
