package seedu.ichifund.logic.tasks;

import seedu.ichifund.model.Model;

/**
 * Represents a task with hidden internal logic and the ability to be executed.
 *
 * Note that {@code Task} is different from {@code Command} in that task are not
 * immediately triggered by user interaction.
 *
 * For instance, when a transaction is added, a task would be triggered to refresh
 * the budget information.
 */
public abstract class Task {

    /**
     * Executes the task.
     *
     * @param model {@code Model} which the task should operate on.
     */
    public abstract void execute(Model model);

}
