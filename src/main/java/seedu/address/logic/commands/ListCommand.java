package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandSubType;
import seedu.address.model.Model;

/**
 * Updates the GUI to list all entries of a specified type to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches the current view to list all contacts or activities. "
            + "If an argument is supplied with a parameter, it is ignored.\n"
            + "Parameters: " + PREFIX_CONTACT + " OR " + PREFIX_ACTIVITY + "\n"
            + "Example: list " + PREFIX_CONTACT;

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    private final CommandSubType type;

    public ListCommand(CommandSubType type) {
        requireNonNull(type);
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && type.equals(((ListCommand) other).type)); // state check
    }
}
