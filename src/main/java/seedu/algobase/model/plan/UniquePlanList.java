package seedu.algobase.model.plan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.model.plan.exceptions.DuplicatePlanException;
import seedu.algobase.model.plan.exceptions.PlanNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

public class UniquePlanList implements Iterable<Plan> {

    private final ObservableList<Plan> internalList = FXCollections.observableArrayList();
    private final ObservableList<Plan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Plan as the given argument.
     */
    public boolean contains(Plan toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePlan);
    }

    /**
     * Adds a Plan to the list.
     * The Plan must not already exist in the list.
     */
    public void add(Plan toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePlanException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Plan {@code target} in the list with {@code editedPlan}.
     * {@code target} must exist in the list.
     * The Plan identity of {@code editedPlan} must not be the same as another existing Plan in the list.
     */
    public void setPlan(Plan target, Plan editedPlan) {
        requireAllNonNull(target, editedPlan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PlanNotFoundException();
        }

        if (!target.isSamePlan(editedPlan) && contains(editedPlan)) {
            throw new DuplicatePlanException();
        }

        internalList.set(index, editedPlan);
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

    public void setPlans(UniquePlanList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Plans}.
     * {@code Plans} must not contain duplicate Plans.
     */
    public void setPlans(List<Plan> Plans) {
        requireAllNonNull(Plans);
        if (!PlansAreUnique(Plans)) {
            throw new DuplicatePlanException();
        }

        internalList.setAll(Plans);
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
                || (other instanceof UniquePlanList // instanceof handles nulls
                && internalList.equals(((UniquePlanList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Plans} contains only unique Plans.
     */
    private boolean PlansAreUnique(List<Plan> Plans) {
        for (int i = 0; i < Plans.size() - 1; i++) {
            for (int j = i + 1; j < Plans.size(); j++) {
                if (Plans.get(i).isSamePlan(Plans.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
