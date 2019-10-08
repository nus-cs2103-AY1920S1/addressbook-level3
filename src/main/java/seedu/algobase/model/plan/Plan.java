package seedu.algobase.model.plan;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.algobase.model.task.Task;

/**
 * Represents a Plan in the algobase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Plan {

    // Identity fields
    private final Name name;

    // Data fields
    private final Description description;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Set<Task> tasks;

    /**
     * Every field must be present and not null.
     */
    public Plan(Name name, Description description, LocalDateTime startDate, LocalDateTime endDate,
                Set<Task> tasks) {
        requireAllNonNull(name, description, startDate, endDate, tasks);
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = new HashSet<>();
        this.tasks.addAll(tasks);
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Returns an immutable problem set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks);
    }

    /**
     * Returns true if both plans of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two plans.
     */
    public boolean isSamePlan(Plan otherPlan) {
        if (otherPlan == this) {
            return true;
        }

        return otherPlan != null
                && otherPlan.getName().equals(getName());
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
        return otherPlan.getName().equals(getName())
                && otherPlan.getDescription().equals(getDescription())
                && otherPlan.getStartDate().equals(getStartDate())
                && otherPlan.getEndDate().equals(getEndDate())
                && otherPlan.getTasks().equals(getTasks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, tasks, startDate, endDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" Tasks: ");
        getTasks().forEach(builder::append);
        return builder.toString();
    }

}
