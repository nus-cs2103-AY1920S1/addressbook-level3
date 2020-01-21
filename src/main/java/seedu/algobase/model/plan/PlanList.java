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
    private final IntegerProperty doneCount = new SimpleIntegerProperty();
    private final IntegerProperty undoneCount = new SimpleIntegerProperty();
    private final IntegerProperty taskCount = new SimpleIntegerProperty();

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
        internalList.forEach(plan -> updatedList.add(plan.removeProblem(problem)));
        setPlans(updatedList);
    }

    /**
     * Updates the given problem in all tasks.
     */
    public void updateProblem(Problem oldProblem, Problem newProblem) {
        List<Plan> updatedList = new ArrayList<>();
        internalList.forEach(plan -> updatedList.add(plan.updateProblem(oldProblem, newProblem)));
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
        clearCurrentPlan();
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
            clearCurrentPlan();
        }
    }

    /**
     * Returns the current {@code Plan}.
     */
    public StringProperty getCurrentPlan() {
        return currentPlan;
    }

    /**
     * Sets the current plan as identified by the given index.
     */
    public void setCurrentPlan(int index) {
        setCurrentPlan(internalList.get(index));
    }

    /**
     * Sets the current {@code Plan}.
     */
    public void setCurrentPlan(Plan plan) {
        if (plan != null) {
            currentPlan.set(plan.getPlanName().fullName);
            doneCount.set(plan.getDoneTaskCount());
            undoneCount.set(plan.getUndoneTaskCount());
            taskCount.set(plan.getDoneTaskCount() + plan.getUndoneTaskCount());
            internalTaskList.setAll(plan.getTaskList());
        } else {
            clearCurrentPlan();
        }
    }

    /**
     * Sets the current {@code Plan} as empty.
     */
    private void clearCurrentPlan() {
        currentPlan.set("");
        doneCount.set(0);
        undoneCount.set(0);
        taskCount.set(0);
        internalTaskList.setAll();
    }

    /**
     * Returns the number of done tasks in current plan.
     */
    public IntegerProperty getCurrentDoneCount() {
        return doneCount;
    }

    /**
     * Returns the number of undone tasks in current plan.
     */
    public IntegerProperty getCurrentUndoneCount() {
        return undoneCount;
    }

    /**
     * Returns the total number of tasks in current plan.
     */
    public IntegerProperty getCurrentTaskCount() {
        return taskCount;
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
