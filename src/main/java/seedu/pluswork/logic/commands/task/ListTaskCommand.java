package seedu.pluswork.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "list-tasks";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
