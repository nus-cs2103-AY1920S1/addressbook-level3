package seedu.moolah.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.expense.Event;

/**
 * Adds an event to the MooLah.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.ADD + CommandGroup.EVENT;

    public static final String COMMAND_DESCRIPTION = "Add event %1$s (%2$s)";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an event to MooLah. \n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_TIMESTAMP + "TIMESTAMP\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Chicken Rice "
            + PREFIX_PRICE + "3.50 "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_TIMESTAMP + "25-12";

    public static final String MESSAGE_SUCCESS = "New event added:\n %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in MooLah!";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}.
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, toAdd.getDescription(), toAdd.getPrice());
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
