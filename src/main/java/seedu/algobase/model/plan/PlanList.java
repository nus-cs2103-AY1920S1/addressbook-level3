package seedu.algobase.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.model.plan.exceptions.PlanNotFoundException;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;

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
    private final ObservableList<Task> internalTaskList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableTaskList =
        FXCollections.unmodifiableObservableList(internalTaskList);
    private final StringProperty currentPlan = new SimpleStringProperty();
    private final IntegerProperty solvedCount = new SimpleIntegerProperty();
    private final IntegerProperty unsolvedCount = new SimpleIntegerProperty();

    /**
     * Check whether any plan in the list contains the given problem.
     * @return true/false based on whether the given problem is contained in one of the plans.
     */
    public boolean containsProblem(Problem problem) {
        return internalList.stream().anyMatch(plan -> plan.containsProblem(problem));
    }

    /**
     * Removes the given problem from all tasks.
     */
    public void removeProblem(Problem problem) {
        List<Plan> updatedList = new ArrayList<>();
        internalList.stream().forEach(plan -> updatedList.add(plan.removeProblem(problem)));
        setPlans(updatedList);
    }

    /**
     * Adds a Plan to the list.
     */
    public void add(Plan toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        setCurrentPlan(toAdd);
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
        setCurrentPlan(updatedPlan);
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
        currentPlan.set("");
        solvedCount.set(0);
        unsolvedCount.set(0);
        internalTaskList.setAll();
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    public void setPlans(PlanList replacement) {
        requireNonNull(replacement);
        setPlans(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code plans}.
     */
    public void setPlans(List<Plan> plans) {
        requireAllNonNull(plans);
        internalList.setAll(plans);

        if (plans.size() > 0) {
            // Default to first plan in list
            setCurrentPlan(plans.get(0));
        } else {
            currentPlan.set("");
        }
    }

    /**
     * Returns the current {@code Plan}.
     */
    public StringProperty getCurrentPlan() {
        return currentPlan;
    }

    /**
     * Sets the current {@code Plan}.
     */
    public void setCurrentPlan(Plan plan) {
        currentPlan.set(plan.getPlanName().fullName);
        solvedCount.set(plan.getSolvedTaskCount());
        unsolvedCount.set(plan.getUnsolvedTaskCount());
        internalTaskList.setAll(plan.getTaskList());
    }

    /**
     * Returns the number of solved tasks in current plan.
     */
    public IntegerProperty getCurrentSolvedCount() {
        return solvedCount;
    }

    /**
     * Returns the number of solved tasks in current plan.
     */
    public IntegerProperty getCurrentUnsolvedCount() {
        return unsolvedCount;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Plan> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the backing task list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> getUnmodifiableObservableTaskList() {
        return internalUnmodifiableTaskList;
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
