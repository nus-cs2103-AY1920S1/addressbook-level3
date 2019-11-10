package seedu.algobase.model.plan;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.algobase.model.Id;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;

/**
 * Represents a Plan in the algobase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Plan {

    // Identity fields
    private final Id id;
    private final PlanName planName;

    // Data fields
    private final PlanDescription planDescription;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Set<Task> tasks;

    /**
     * Every field must be present and not null.
     */
    public Plan(PlanName planName, PlanDescription planDescription, LocalDate startDate, LocalDate endDate,
                Set<Task> tasks) {
        requireAllNonNull(planName, planDescription, startDate, endDate, tasks);
        this.id = Id.generateId();
        this.planName = planName;
        this.planDescription = planDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = new HashSet<>();
        this.tasks.addAll(tasks);
    }

    public Plan(Id id, PlanName planName, PlanDescription planDescription, LocalDate startDate,
                LocalDate endDate, Set<Task> tasks) {
        requireAllNonNull(id, planName, planDescription, startDate, endDate, tasks);
        this.id = id;
        this.planName = planName;
        this.planDescription = planDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = new HashSet<>();
        this.tasks.addAll(tasks);
    }

    /**
     * Creates and returns a {@code Plan} with the details of the original plan
     * with an updated {@code taskSet}.
     */
    public Plan updateTasks(Set<Task> taskSet) {
        requireAllNonNull(taskSet);

        return new Plan(id, planName, planDescription, startDate, endDate, taskSet);
    }

    /**
     * Check whether a given date lies inside its own date range.
     * @return true/false based on whether the given date is within plan date range.
     */
    public boolean checkIsWithinDateRange(LocalDate date) {
        requireAllNonNull(date);

        return date.compareTo(this.getStartDate()) >= 0 && date.compareTo(this.getEndDate()) <= 0;
    }

    /**
     * Check whether its tasks contain the given problem.
     * @return true/false based on whether the given problem is contained in one of its tasks.
     */
    boolean containsProblem(Problem problem) {
        requireAllNonNull(problem);

        return this.tasks.stream().anyMatch(task -> task.getProblem().equals(problem));
    }

    /**
     * Deletes the given problem from all tasks.
     */
    Plan removeProblem(Problem problem) {
        requireAllNonNull(problem);

        Set<Task> taskSet = this.tasks.stream().filter(task -> !task.getProblem().equals(problem))
            .collect(Collectors.toSet());
        return this.updateTasks(taskSet);
    }

    /**
     * Updates the given problem in all tasks.
     */
    Plan updateProblem(Problem oldProblem, Problem newProblem) {
        Set<Task> taskSet = new HashSet<>();
        this.tasks.forEach(task -> {
            if (task.getProblem().equals(oldProblem)) {
                taskSet.add(task.updateProblem(newProblem));
            } else {
                taskSet.add(task);
            }
        });
        return this.updateTasks(taskSet);
    }

    /**
     * Returns number of done tasks within plan.
     */
    int getDoneTaskCount() {
        return (int) this.getTasks().stream().filter(Task::getIsDone).count();
    }

    /**
     * Returns number of undone tasks within plan.
     */
    int getUndoneTaskCount() {
        return (int) this.getTasks().stream().filter((task) -> !task.getIsDone()).count();
    }

    public Id getId() {
        return id;
    }

    public PlanName getPlanName() {
        return planName;
    }

    public PlanDescription getPlanDescription() {
        return planDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Returns an immutable task set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks);
    }

    /**
     * Returns a list of all tasks, sorted by name.
     */
    public List<Task> getTaskList() {
        List<Task> taskList = new ArrayList<>(this.getTasks());
        taskList.sort(Comparator.comparing(Task::getName));
        return taskList;
    }

    /**
     * Returns true if both plans of the same planName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two plans.
     */
    public boolean isSamePlan(Plan otherPlan) {
        if (otherPlan == this) {
            return true;
        }

        return otherPlan != null
                && otherPlan.getPlanName().equals(getPlanName());
    }

    /**
     * Returns true if both plans have the same identity and data fields.
     * This defines a stronger notion of equality between two plans.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Plan)) {
            return false;
        }

        Plan otherPlan = (Plan) other;
        return otherPlan.getPlanName().equals(getPlanName())
                && otherPlan.getPlanDescription().equals(getPlanDescription())
                && otherPlan.getStartDate().equals(getStartDate())
                && otherPlan.getEndDate().equals(getEndDate())
                && otherPlan.getTasks().equals(getTasks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(planName, planDescription, startDate, endDate, tasks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPlanName())
                .append(" Description: ")
                .append(getPlanDescription())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" Tasks: ");
        getTasks().forEach(builder::append);
        return builder.toString();
    }

}
