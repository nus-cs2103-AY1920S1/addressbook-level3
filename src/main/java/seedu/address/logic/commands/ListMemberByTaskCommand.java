package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListMemberByTaskCommand extends Command {

    public static final String COMMAND_WORD = "list-member-by-task";

    public static final String MESSAGE_SUCCESS = "Listed all members by task";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
