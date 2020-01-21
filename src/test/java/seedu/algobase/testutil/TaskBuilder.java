package seedu.algobase.testutil;

import java.time.LocalDate;

import seedu.algobase.model.Id;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    private Id id;
    private Problem problem = new ProblemBuilder().build();
    private LocalDate dueDate;
    private boolean isDone;

    public TaskBuilder() {
        dueDate = LocalDate.now();
        isDone = false;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        problem = taskToCopy.getProblem();
        dueDate = taskToCopy.getDueDate();
        isDone = taskToCopy.getIsDone();
    }

    /**
     * Sets the {@code Id} of the {@code Task} that we are building.
     */
    public TaskBuilder withId(String id) {
        this.id = new Id(id);
        return this;
    }

    /**
     * Sets the {@code Problem} of the {@code Task} that we are building.
     */
    public TaskBuilder withProblem(Problem problem) {
        this.problem = problem;
        return this;
    }

    /**
     * Sets the {@code dueDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    /**
     * Sets the {@code isDone} of the {@code Task} that we are building.
     */
    public TaskBuilder withIsDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public Task build() {
        return new Task(problem, dueDate, isDone);
    }

}
