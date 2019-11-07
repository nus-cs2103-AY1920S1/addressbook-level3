package seedu.guilttrip.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.CategoryList;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.messages.Notification;

/**
 * Unmodifiable view of an guilttrip book
 */
public interface ReadOnlyGuiltTrip extends Observable {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain
     * any duplicate persons.
     */
    CategoryList getCategoryList();

    ObservableList<Category> getExpenseCategoryList();

    ObservableList<Category> getIncomeCategoryList();

    ObservableList<Expense> getExpenseList();

    ObservableList<Income> getIncomeList();

    ObservableList<Wish> getWishList();

    ObservableList<Budget> getBudgetList();

    ObservableList<AutoExpense> getAutoExpenseList();

    ObservableList<Reminder> getReminderList();

    ObservableList<Condition> getConditionList();

    ObservableList<Notification> getNotificationList();

}
