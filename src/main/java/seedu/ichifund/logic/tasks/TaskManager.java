package seedu.ichifund.logic.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.logic.tasks.budget.ComputeBudgetTask;
import seedu.ichifund.model.Model;

/**
 * Manages all the task to be executed.
 */
public class TaskManager {

    private final List<Task> tasks;
    private final Logger logger = LogsCenter.getLogger(TaskManager.class);

    public TaskManager() {
        tasks = new ArrayList<>();
        tasks.add(new ComputeBudgetTask());
    }

    /**
     * Executes all  task.
     *
     * @param model {@code Model} which all tasks should operate on.
     */
    public void executeAll(Model model) {
        tasks.forEach(task -> {
            task.execute(model);
            logger.info("----------------[TASK EXECUTED][" + task.getClass().getName() + "]");
        });
    }

}
