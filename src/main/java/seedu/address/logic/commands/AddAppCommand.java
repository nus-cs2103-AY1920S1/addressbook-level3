package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE_TIMES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.predicates.EventContainsRefIdPredicate;


/**
 * Adds a person to the address book.
 */
public class AddAppCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a appointment to the address book. "
            + "Parameters: "
            + PREFIX_ID + "REFERENCE ID "
            + PREFIX_START + "PREFIX_EVENT "
            + PREFIX_END + "PREFIX_EVENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "001A "
            + PREFIX_START + "01/11/19 1800 "
            + PREFIX_END + "01/11/19 1900";

    public static final String MESSAGE_USAGE_RECURSIVELY = COMMAND_WORD + ": Adds recursively appointment"
            + " to the address book. \n"
            + "Parameters: "
            + PREFIX_ID + "REFERENCE ID "
            + "[" + PREFIX_RECURSIVE + "PREFIX_RECURSIVE w/m/y] "
            + "[" + PREFIX_RECURSIVE_TIMES + "PREFIX_RECURSIVE_TIMES] "
            + PREFIX_START + "PREFIX_EVENT "
            + PREFIX_END + "PREFIX_EVENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "001A "
            + PREFIX_RECURSIVE + "m "
            + PREFIX_RECURSIVE_TIMES + "2 "
            + PREFIX_START + "01/11/19 1800 "
            + PREFIX_END + "01/11/19 1900";

    public static final String MESSAGE_SUCCESS = "Appointment added: %1$s";
    public static final String MESSAGE_SUCCESS_RECURSIVE = " recusive Appointments were added";
    public static final String MESSAGE_DUPLICATE_EVENT = "This appointment is already scheduled: %1$s";
    public static final String MESSAGE_CLASH_APPOINTMENT = "This appointment clashes with a pre-existing appointment.";

    private final Event toAdd;
    private final List<Event> eventList;

    /**
     * Creates an AddAppCommand to add the specified {@code Person}
     */
    public AddAppCommand(Event toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.eventList = null;

    }

    public AddAppCommand(List<Event> eventList) {
        requireNonNull(eventList);
        this.toAdd = null;
        this.eventList = eventList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (eventList == null) {
            model.scheduleAppointment(toAdd);
            model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(toAdd.getPersonId()));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

        }

        //TODO: Should it still add the other appointments if one fails?
        for (Event e : eventList) {
            model.scheduleAppointment(e);
        }
        model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(eventList.get(0).getPersonId()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventList.size() + MESSAGE_SUCCESS_RECURSIVE));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppCommand // instanceof handles nulls
                && toAdd.equals(((AddAppCommand) other).toAdd));
    }
}
