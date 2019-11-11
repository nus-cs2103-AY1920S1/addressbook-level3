package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_EVENT;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates a new event to be added to the event list.
 */
public class EventAddCommand extends EventCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new event\n"
            + "Parameters:\n"
            + "eventName/ [EVENTNAME]\n"
            + "startDateTime/ [STARTDATETIME]\n"
            + "endDateTime/ [ENDDATETIME]\n"
            + "recur/ [DAILY/WEEKLY/NONE]\n"
            + "color/ [0 - 23]\n"
            + "Example: event eventName/cs2103 Practical startDateTime/2019-11-15T08:00 "
            + "endDateTime/2019-11-15T09:00 recur/none color/1";
    public static final String MESSAGE_SUCCESS = "Added Event: %1$s";

    private final VEvent toAdd;

    /**
     * Constructor for event add command
     * @param toAdd VEvent to be added.
     */
    public EventAddCommand(VEvent toAdd) {
        requireNonNull(toAdd);

        this.toAdd = toAdd;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.hasVEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } else {
            model.addVEvent(toAdd);
            return new CommandResult(generateSuccessMessage(toAdd), CommandResultType.SHOW_SCHEDULE);
        }
    }

    /**
     * Generates a command execution success message.
     *
     * @param vEvent that has been added.
     */
    private String generateSuccessMessage(VEvent vEvent) {
        return String.format(MESSAGE_SUCCESS, vEvent.getSummary().getValue());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventAddCommand)) {
            return false;
        }

        // state check
        EventAddCommand e = (EventAddCommand) other;
        return toAdd.equals(e.toAdd);
    }
}
