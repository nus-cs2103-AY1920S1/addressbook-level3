package seedu.algobase.model.task;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import seedu.algobase.model.Id;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;

/**
 * Represents a Task in the algobase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    private final Id id;
    private final Problem problem;
    private final Boolean isSolved;
    private final LocalDate targetDate;

    /**
     * Problem field must be present and not null.
     */
    public Task(Problem problem, LocalDate targetDate, boolean isSolved) {
        requireAllNonNull(problem);
        this.id = Id.generateId();
        this.problem = problem;
        this.isSolved = isSolved;
        this.targetDate = targetDate;
    }

    public Task(Id id, Problem problem, LocalDate targetDate, boolean isSolved) {
        requireAllNonNull(id, problem);
        this.id = id;
        this.problem = problem;
        this.isSolved = isSolved;
        this.targetDate = targetDate;
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * with an updated {@code isSolved}.
     */
    public static Task updateStatus(Task taskToUpdate, boolean isSolved) {
        requireAllNonNull(taskToUpdate, isSolved);

        Id id = taskToUpdate.id;
        Problem problem = taskToUpdate.problem;
        LocalDate targetDate = taskToUpdate.targetDate;

        return new Task(id, problem, targetDate, isSolved);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * with an updated {@code targetDate}.
     */
    public static Task updateDueDate(Task taskToUpdate, LocalDate targetDate) {
        requireAllNonNull(taskToUpdate, targetDate);

        Id id = taskToUpdate.id;
        Problem problem = taskToUpdate.problem;
        boolean isSolved = taskToUpdate.isSolved;

        return new Task(id, problem, targetDate, isSolved);
    }

    public Id getId() {
        return id;
    }

    public Problem getProblem() {
        return problem;
    }

    public Name getName() {
        return problem.getName();
    }

    public Author getAuthor() {
        return problem.getAuthor();
    }

    public Description getDescription() {
        return problem.getDescription();
    }

    public WebLink getWebLink() {
        return problem.getWebLink();
    }

    public Difficulty getDifficulty() {
        return problem.getDifficulty();
    }

    public Remark getRemark() {
        return problem.getRemark();
    }

    public Source getSource() {
        return problem.getSource();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return problem.getTags();
    }

    public Boolean getIsSolved() {
        return isSolved;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    /**
     * Returns status icon of the task.
     *
     * @return the tick icon [✓] if task is done, or cross icon [✗] otherwise.
     */
    public String getStatusIcon() {
        //CHECKSTYLE.OFF: AvoidEscapedUnicodeCharactersCheck
        return this.isSolved ? "[\u2713]" : "[\u2718]";
        //CHECKSTYLE.ON: AvoidEscapedUnicodeCharactersCheck
    }

    /**
     * Returns true if both tasks have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getProblem().equals(getProblem());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(problem);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Problem: ")
            .append(getProblem())
            .append(" Date: ")
            .append(getTargetDate())
            .append(" isSolved: ")
            .append(getIsSolved());
        return builder.toString();
    }

}
