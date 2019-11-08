package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_BIG_RANGE;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_START_AFTER_END;
import static seedu.address.commons.core.Messages.MESSAGE_EVENTS_DUPLICATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.ui.MainWindow;

/**
 * Adds an {@code Event} to the EventBook.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "add_ev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the event book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_VENUE + "VENUE "
            + PREFIX_EVENT_MANPOWER_NEEDED + "MANPOWER NEEDED "
            + PREFIX_EVENT_START_DATE + "START_DATE "
            + PREFIX_EVENT_END_DATE + "END_DATE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Free Coffee "
            + PREFIX_EVENT_VENUE + "Utown Starbucks "
            + PREFIX_EVENT_MANPOWER_NEEDED + "5 "
            + PREFIX_EVENT_START_DATE + "16/03/2019 "
            + PREFIX_EVENT_END_DATE + "17/03/2019 "
            + PREFIX_TAG + "fun "
            + PREFIX_TAG + "free ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_INVALID_DATES = "Invalid start/end dates!";

    private final Event toAdd;


    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EVENT_LIST);
        }

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_EVENTS_DUPLICATE);
        }

        EventDate eventStartDate = toAdd.getStartDate();
        EventDate eventEndDate = toAdd.getEndDate();
        long dateDifference = eventStartDate.dateDifference(eventEndDate);

        if (dateDifference < 0) {
            throw new CommandException(
                    String.format(MESSAGE_DATE_START_AFTER_END, eventStartDate, eventEndDate));
        } else if (dateDifference > 90) {
            throw new CommandException(String.format(
                    MESSAGE_DATE_BIG_RANGE, eventStartDate, eventEndDate, dateDifference));
        }

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

