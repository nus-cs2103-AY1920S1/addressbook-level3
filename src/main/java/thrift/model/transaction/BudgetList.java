package thrift.model.transaction;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * A list of {@code Budget} that does not allow nulls. A unique month-year can contain one budget set, which can be
 * changed by the user.
 */
public class BudgetList implements Iterable<Budget> {

    private final List<Budget> internalList;

    public BudgetList() {
        this.internalList = new ArrayList<>();
    }

    /**
     * Returns an optional budget tied to the given {@code Calendar} date.
     */
    public Optional<Budget> getBudgetForMonthYear(Calendar toCheck) {
        requireNonNull(toCheck);
        for (Budget b : internalList) {
            if (b.getBudgetDate().get(Calendar.MONTH) == toCheck.get(Calendar.MONTH)
                && b.getBudgetDate().get(Calendar.YEAR) == toCheck.get(Calendar.YEAR)) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    /**
     * Adds or updates an existing {@code Budget} in the budget list with {@code toSet}.
     *
     * @param toSet budget to be replaced with.
     * @return replaced budget wrapped in optional.
     */
    public Optional<Budget> setBudget(Budget toSet) {
        requireNonNull(toSet);
        Optional<Budget> optBudget = getBudgetForMonthYear(toSet.getBudgetDate());
        if (optBudget.isPresent()) {
            internalList.set(internalList.indexOf(optBudget.get()), toSet);
        } else {
            internalList.add(toSet);
        }
        return optBudget;
    }

    /**
     * Removes {@code budget} from the list.
     *
     * @param budget budget to be removed from the list.
     */
    public void removeBudget(Budget budget) {
        requireNonNull(budget);
        internalList.remove(budget);
    }

    /**
     * Replaces the content of this budget list with {@code replacement}.
     */
    public void setBudgets(BudgetList replacement) {
        internalList.clear();
        internalList.addAll(replacement.internalList);
    }

    @Override
    public Iterator<Budget> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetList) //instanceof handles nulls
                        && internalList.equals(((BudgetList) other).internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
