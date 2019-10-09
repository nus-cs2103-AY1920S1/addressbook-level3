package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Serves the next patient in queue.
 */
public class NextCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_SUCCESS = "Next patient has been allocated";
    public static final String MESSAGE_UNDO_NEXT_SUCCESS = "Allocation has been undone";
    public static final String MESSAGE_UNDO_NEXT_ERROR = "Changes cannot be undone!";

    private Person personToDelete;

    public NextCommand() {
        this.personToDelete = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("dsadsad");
    }

    @Override
    public CommandResult undo(Model model) {
        return new CommandResult("dsadsad");
    }
}
