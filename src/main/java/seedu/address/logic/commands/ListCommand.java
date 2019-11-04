package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import seedu.address.logic.CommandSubType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
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

    public static final String MESSAGE_SUCCESS = "Listed all %s";
    public static final String MESSAGE_UNKNOWN_LIST_TYPE = "List command has unknown type!";

    private final CommandSubType type;

    public ListCommand(CommandSubType type) {
        requireNonNull(type);
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Contextual behaviour
        switch (this.type) {
        case CONTACT:
            Context newContactContext = Context.newListContactContext();

            model.setContext(newContactContext);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_ENTRIES);

            return new CommandResult(String.format(MESSAGE_SUCCESS, "contacts"), newContactContext);
        case ACTIVITY:
            Context newActivityContext = Context.newListActivityContext();

            model.setContext(newActivityContext);
            model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ENTRIES);

            return new CommandResult(String.format(MESSAGE_SUCCESS, "activities"), newActivityContext);
        default:
            throw new CommandException(MESSAGE_UNKNOWN_LIST_TYPE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && type.equals(((ListCommand) other).type)); // state check
    }
}
