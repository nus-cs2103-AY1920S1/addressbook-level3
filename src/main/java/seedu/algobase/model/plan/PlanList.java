package seedu.algobase.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.model.plan.exceptions.PlanNotFoundException;

/**
 * A list of plans.
 *
 * Supports a minimal set of list operations.
 *
 * @see Plan#isSamePlan(Plan)
 */
public class PlanList implements Iterable<Plan> {

    private final ObservableList<Plan> internalList = FXCollections.observableArrayList();
    private final ObservableList<Plan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a Plan to the list.
     */
    public void add(Plan toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the Plan {@code target} in the list with {@code editedPlan}.
     * {@code target} must exist in the list.
     */
    public void setPlan(Plan target, Plan updatedPlan) {
        requireAllNonNull(target, updatedPlan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PlanNotFoundException();
        }

        internalList.set(index, updatedPlan);
    }

    /**
     * Removes the equivalent Plan from the list.
     * The Plan must exist in the list.
     */
    public void remove(Plan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PlanNotFoundException();
        }
    }

    public void setPlans(PlanList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code plans}.
     * {@code plans} must not contain duplicate plans.
     */
    public void setPlans(List<Plan> plans) {
        requireAllNonNull(plans);
        internalList.setAll(plans);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Plan> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Plan> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlanList // instanceof handles nulls
                        && internalList.equals(((PlanList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if the list contains an equivalent Plan as the given argument.
     */
    public boolean contains(Plan toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePlan);
    }

}
