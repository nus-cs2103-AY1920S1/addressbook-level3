package seedu.algobase.logic.commands;

import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.commands.problem.AddCommand;
import seedu.algobase.logic.commands.problem.DeleteCommand;
import seedu.algobase.logic.commands.problem.EditCommand;
import seedu.algobase.logic.commands.problem.FindCommand;
import seedu.algobase.logic.commands.problem.ListCommand;
import seedu.algobase.logic.commands.problem.SortCommand;
import seedu.algobase.logic.commands.task.AddTaskCommand;
import seedu.algobase.logic.commands.task.CopyTaskCommand;
import seedu.algobase.logic.commands.task.DeleteTaskCommand;
import seedu.algobase.logic.commands.task.DoneTaskCommand;
import seedu.algobase.logic.commands.task.EditTaskCommand;
import seedu.algobase.logic.commands.task.MoveTaskCommand;
import seedu.algobase.logic.commands.task.SetPlanCommand;
import seedu.algobase.logic.commands.task.UndoneTaskCommand;
import seedu.algobase.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final Class[] COMMAND_LIST = {
        AddCommand.class,
        AddPlanCommand.class,
        AddTagCommand.class,
        AddTaskCommand.class,
        ClearCommand.class,
        CopyTaskCommand.class,
        DeleteCommand.class,
        DeletePlanCommand.class,
        DeleteTagCommand.class,
        DeleteTaskCommand.class,
        DoneTaskCommand.class,
        EditCommand.class,
        EditPlanCommand.class,
        EditTagCommand.class,
        EditTaskCommand.class,
        ExitCommand.class,
        FindCommand.class,
        FindPlanCommand.class,
        HelpCommand.class,
        ListCommand.class,
        ListPlanCommand.class,
        ListTagCommand.class,
        MoveTaskCommand.class,
        SetPlanCommand.class,
        SortCommand.class,
        SwitchTabCommand.class,
        UndoneTaskCommand.class
    };

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

}
