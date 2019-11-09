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
    private boolean isSolved;

    public TaskBuilder() {
        dueDate = LocalDate.now();
        isSolved = false;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        problem = taskToCopy.getProblem();
        dueDate = taskToCopy.getTargetDate();
        isSolved = taskToCopy.getIsSolved();
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
     * Sets the {@code isSolved} of the {@code Task} that we are building.
     */
    public TaskBuilder withIsSolved(boolean isSolved) {
        this.isSolved = isSolved;
        return this;
    }

    public Task build() {
        return new Task(problem, dueDate, isSolved);
    }

}
