package seedu.address.model.budget;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.expense.Expense;

/**
 * Wraps all data at the budgetList level
 * Duplicates are not allowed (by .isSameBudget comparison)
 */
public class BudgetList implements ReadOnlyBudgetList {

    private final UniqueBudgetList budgets;

     /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        budgets = new UniqueBudgetList();
    }

    public BudgetList() {
    }

    /**
     * Creates an BudgetList using the Budgets in the {@code toBeCopied}
     */
    public BudgetList(ReadOnlyBudgetList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the budget list with {@code budgets}.
     * {@code budgets} must not contain duplicate budgets.
     */
    public void setBudgets(List<Budget> budgets) {
        this.budgets.setBudgets(budgets);
    }

    /**
     * Resets the existing data of this {@code BudgetList} with {@code newData}.
     */
    public void resetData(ReadOnlyBudgetList newData) {
        requireNonNull(newData);

        setBudgets(newData.getBudgetList());
    }

    //// budget-level operations

    /**
     * Returns true if a budget with the same identity as {@code budget} exists in the budget list.
     */
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return budgets.contains(budget);
    }

    /**
     * Adds a budget to the budget list.
     * The budget must not already exist in the budget list.
     */
    public void addBudget(Budget p) {
        budgets.add(p);
    }

    /**
     * Replaces the given budget {@code target} in the list with {@code editedBudget}.
     * {@code target} must exist in the budget list.
     * The budget identity of {@code editedBudget} must not be the same as another existing budget in the budget
     * list.
     */
    public void setBudget(Budget target, Budget editedBudget) {
        requireNonNull(editedBudget);

        budgets.setBudget(target, editedBudget);
    }

    /**
     * Removes {@code key} from this {@code BudgetList}.
     * {@code key} must exist in the budget list.
     */
    public void removeBudget(Budget key) {
        budgets.remove(key);
    }

    //// util methods

    public Optional<Budget> getBudgetExpenseFallsInto(Expense expense) {
        Optional<Budget> toReturn = Optional.empty();
        List<Budget> lastShownList = getBudgetList();
        for (Budget budget : lastShownList) {
            if (budget.isDateWithinBudgetPeriod(expense.getDate())) {
                toReturn = Optional.of(budget);
                break;
            }
        }
        return toReturn;
    }

    /**
     * Checks whether a new budget to be added clashes with any existing budget period.
     * @param newBudget a valid budget.
     * @return a boolean value.
     */
    public boolean hasBudgetPeriodClash(Budget newBudget) {
        List<Budget> lastShownList = getBudgetList();
        for (Budget budget : lastShownList) {
            if (budget.isDateWithinBudgetPeriod(newBudget.getStartDate())
                    || budget.isDateWithinBudgetPeriod(newBudget.getEndDate())
                    || budget.doesOtherBudgetOverlap(newBudget)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return budgets.asUnmodifiableObservableList().size() + " budgets";
        // TODO: refine later
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return budgets.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BudgetList // instanceof handles nulls
            && budgets.equals(((BudgetList) other).budgets));
    }

    @Override
    public int hashCode() {
        return budgets.hashCode();
    }
}
