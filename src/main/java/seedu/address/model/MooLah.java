package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.UniqueBudgetList;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.expense.UniqueEventList;
import seedu.address.model.expense.UniqueExpenseList;

/**
 * Wraps all data at the MooLah level
 * Duplicates are not allowed (by .isSameExpense comparison)
 */
public class MooLah implements ReadOnlyMooLah {

    private final UniqueExpenseList expenses;
    private final UniqueBudgetList budgets;
    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is a non-static initialization block,
     * sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to
     * avoid duplication among constructors.
     */
    /*{
        expenses = new UniqueExpenseList();
        budgets = new UniqueBudgetList();
        if (budgets.isEmpty()) {
            Budget defaultBudget = Budget.createDefaultBudget();
            defaultBudget.setPrimary();
            budgets.add(defaultBudget);
        }
        events = new UniqueEventList();
    }*/

    public MooLah() {
        expenses = new UniqueExpenseList();
        budgets = new UniqueBudgetList();
        budgets.add(Budget.createDefaultBudget());
        events = new UniqueEventList();
    }

    /**
     * Creates an MooLah using the Expenses in the {@code toBeCopied}
     */
    public MooLah(ReadOnlyMooLah toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the expense list with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses.setExpenses(expenses);
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets.setBudgets(budgets);
    }

    /**
     * Replaces the contents of the expense list with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code MooLah} with {@code newData}.
     */
    public void resetData(ReadOnlyMooLah newData) {
        requireNonNull(newData);

        setExpenses(newData.getExpenseList());
        setBudgets(newData.getBudgetList());
        setEvents(newData.getEventList());
        resetBudgetExpenseLists();
    }

    /**
     * Update expense list of all budgets as a result of UndoCommand on expenses.
     */
    private void resetBudgetExpenseLists() {
        for (Budget b : budgets) {
            b.clearExpenses();
        }
        for (Expense e : expenses) {
            Budget b = budgets.getBudgetWithName(e.getBudgetName());
            if (b != null) {
                b.addExpense(e);
            }
        }
    }

    //=========== Expense-level operations =============================================================

    /**
     * Returns true if an expense with the same identity as {@code expense}
     * exists in the MooLah.
     */
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenses.contains(expense);
    }

    /**
     * Adds an expense to the MooLah.
     * The expense must not already exist in the MooLah.
     */
    public void addExpense(Expense p) {
        if (p.getBudgetName() == null) {
            Budget primaryBudget = budgets.getPrimaryBudget();
            p.setBudget(primaryBudget);
            primaryBudget.addExpense(p);
            primaryBudget.updateProportionUsed();
        } else {
            Budget budget = budgets.getBudgetWithName(p.getBudgetName());
            if (budget != null) {
                budget.addExpense(p);
                budget.updateProportionUsed();
            }
        }
        expenses.add(p);
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the MooLah.
     * The expense identity of {@code editedExpense} must not be the same as another existing
     * expense in the MooLah.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);

        expenses.setExpense(target, editedExpense);
        for (Budget budget : budgets) {
            budget.setExpense(target, editedExpense);
        }
    }

    /**
     * Removes {@code key} from this {@code MooLah}.
     * {@code key} must exist in the MooLah.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
        for (Budget budget : budgets) {
            budget.removeExpense(key);
        }
    }

    //=========== Budget-level operations ================================================================
    /**
     * Returns true if a budget with the same identity as {@code budget}
     * exists in Moolah.
     */
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return budgets.contains(budget);
    }

    /**
     * Adds a budget to Moolah.
     * The budget must not already exist in Moolah.
     */
    public void addBudget(Budget budget) {
        requireNonNull(budget);
        budgets.add(budget);
    }

    public void addBudgetFromStorage(Budget budget) {
        budgets.addBudgetFromStorage(budget);
    }

    /**
     * Checks whether MooLah has a budget with the specified name.
     * @param targetDescription The description (i.e. name) of the budget to check.
     * @return A boolean indicating whether the MooLah has such budget.
     */
    public boolean hasBudgetWithName(Description targetDescription) {
        requireNonNull(targetDescription);
        return budgets.hasBudgetWithName(targetDescription);
    }

    public Budget getBudgetWithName(Description d) {
        return budgets.getBudgetWithName(d);
    }

    /**
     * Returns the primary budget in the MooLah.
     * @return The primary budget in the MooLah.
     */
    public Budget getPrimaryBudget() {
        return budgets.getPrimaryBudget();
    }

    public ObservableObjectValue<Budget> primaryBudgetProperty() {
        return budgets.primaryBudgetPropertyProperty();
    }

    /**
     * Switches the primary budget to the budget with the specified name.
     *
     * @param targetDescription The name of the budget to be switched to.
     */
    public void switchBudgetTo(Description targetDescription) {
        Budget targetBudget = budgets.getBudgetWithName(targetDescription);
        budgets.setPrimary(targetBudget);
    }

    void setBudget(Budget target, Budget editedBudget) {
        requireNonNull(editedBudget);
        budgets.setBudget(target, editedBudget);

        for (Expense expense : expenses) {
            expense.setBudget(editedBudget);
        }
    }

    /**
     * Removes Budget {@code key} from this {@code MooLah}.
     * {@code key} must exist in the MooLah.
     */
    public void removeBudget(Budget key) {
        budgets.remove(key);
        for (Expense expense : expenses) {
            expense.removeBudget();
        }
    }

    /**
     * Changes budget window of primary budget to a period in the past, as specified by the anchor date.
     * @param pastDate The date to anchor the period.
     */
    public void changePrimaryBudgetWindow(Timestamp pastDate) {
        requireNonNull(pastDate);

        budgets.changePrimaryBudgetWindow(pastDate);
    }

    //=========== Event-level operations ================================================================

    /**
     * Returns true if an event with the same identity as {@code event}
     * exists in the MooLah.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the MooLah.
     * The event must not already exist in the MooLah.
     */
    public void addEvent(Event event) {
        if (budgets.isEmpty()) {
            Budget defaultBudget = Budget.createDefaultBudget();
            defaultBudget.setToPrimary();
            budgets.add(defaultBudget);
        }
        Budget primaryBudget = budgets.getPrimaryBudget();
        if (event.getBudgetName() == null) {
            event.setBudget(primaryBudget);
        }
        events.add(event);
    }

    /**
     * Removes {@code key} from this {@code MooLah}.
     * {@code key} must exist in the MooLah.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }
    //// util methods

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in MooLah.
     * The event identity of {@code editedEvent} must not be the same as another existing
     * expense in the MooLah.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
        //        for (Budget budget : budgets) {
        //            budget.setEvent(target, editedEvent);
        //        }
    }

    @Override
    public String toString() {
        return expenses.asUnmodifiableObservableList().size() + " expenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return budgets.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MooLah // instanceof handles nulls
                && expenses.equals(((MooLah) other).expenses));
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }
}
