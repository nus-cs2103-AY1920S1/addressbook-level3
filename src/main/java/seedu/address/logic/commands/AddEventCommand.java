package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_HOURS_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds a person to the address book.
 */
public class AddEventCommand extends EventRelatedCommand {

    public static final String COMMAND_WORD = "addev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the event book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_VENUE + "VENUE "
            + PREFIX_EVENT_HOURS_NEEDED + "HOURS NEEDED "
            + PREFIX_EVENT_MANPOWER_NEEDED + "MANPOWER NEEDED "
            + PREFIX_EVENT_START_DATE + "START DATE"
            + PREFIX_EVENT_END_DATE + "END DATE"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Free Coffee "
            + PREFIX_EVENT_VENUE + "Utown Starbucks "
            + PREFIX_EVENT_HOURS_NEEDED + "6 "
            + PREFIX_EVENT_MANPOWER_NEEDED + "5 "
            + PREFIX_EVENT_START_DATE + "16/03/2019 "
            + PREFIX_EVENT_END_DATE + "17/03/2019 "
            + PREFIX_TAG + "fun "
            + PREFIX_TAG + "free ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book";

    private final Event toAdd;


    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}

