package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POLICIES;

/**
 * Lists the available policies in the address book.
 */
public class ListPolicyCommand extends Command {

    public static final String COMMAND_WORD = "listpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of all current policies.";

    public static final String MESSAGE_SUCCESS = "Listed all policies";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
