package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Event;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: " // need to change later
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_CATEGORY + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Chicken Rice "
            + PREFIX_PRICE + "3.50 "
            + PREFIX_CATEGORY + "nusDeck "
            + PREFIX_CATEGORY + "chicken"
            + PREFIX_TIMESTAMP + "31-12-2019";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the tracker";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}.
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
