package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PROBLEM;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;

/**
 * Adds a Task to a Plan.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to a training plan. "
            + "Parameters: "
            + PREFIX_PLAN + "PLAN "
            + PREFIX_PROBLEM + "PROBLEM\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PLAN + "1 "
            + PREFIX_PROBLEM + "10";

    public static final String MESSAGE_SUCCESS = "New task %1$s added to plan %2$s";

    private final AddTaskDescriptor addTaskDescriptor;

    /**
     * Creates an AddTaskCommand to add a {@code Task} to the specified {@code Plan}
     *
     * @param addTaskDescriptor details of the plan and problem involved
     */
    public AddTaskCommand(AddTaskDescriptor addTaskDescriptor) {
        requireNonNull(addTaskDescriptor);

        this.addTaskDescriptor = addTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownPlanList = model.getFilteredPlanList();
        List<Problem> lastShownProblemList = model.getFilteredProblemList();

        if (addTaskDescriptor.planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }

        if (addTaskDescriptor.problemIndex.getZeroBased() >= lastShownProblemList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROBLEM_DISPLAYED_INDEX);
        }

        Plan plan = lastShownPlanList.get(addTaskDescriptor.planIndex.getZeroBased());
        Problem problem = lastShownProblemList.get(addTaskDescriptor.problemIndex.getZeroBased());
        Task task = new Task(problem);
        plan.getTasks().add(task);

        return new CommandResult(String.format(MESSAGE_SUCCESS, task, plan));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && addTaskDescriptor.equals(((AddTaskCommand) other).addTaskDescriptor));
    }

    /**
     * Stores the details of the plan and problem involced.
     */
    public static class AddTaskDescriptor {
        private Index planIndex;
        private Index problemIndex;

        public AddTaskDescriptor(Index planIndex, Index problemIndex) {
            this.planIndex = planIndex;
            this.problemIndex = problemIndex;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                || (other instanceof AddTaskDescriptor // instanceof handles nulls
                && planIndex.equals(((AddTaskDescriptor) other).planIndex)
                && problemIndex.equals(((AddTaskDescriptor) other).problemIndex));
        }
    }
}
