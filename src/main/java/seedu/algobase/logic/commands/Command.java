package seedu.algobase.logic.commands;

import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final Class[] COMMAND_LIST = {
        AddCommand.class,
        AddPlanCommand.class,
        AddTagCommand.class,
        ClearCommand.class,
        DeleteCommand.class,
        DeletePlanCommand.class,
        DeleteTagCommand.class,
        DeleteTaskCommand.class,
        DoneTaskCommand.class,
        EditCommand.class,
        EditPlanCommand.class,
        EditTagCommand.class,
        ExitCommand.class,
        FindCommand.class,
        FindPlanCommand.class,
        HelpCommand.class,
        ListCommand.class,
        ListPlanCommand.class,
        ListTagCommand.class,
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
