//@@author woon17
package seedu.address.logic.commands.duties;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE_TIMES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.exceptions.InvalidEventScheduleChangeException;
import seedu.address.model.events.predicates.EventContainsRefIdPredicate;

import java.util.List;


/**
 * Adds a person to the address book.
 */
public class AddDutyShiftCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a appointment to the address book. "
            + "Parameters: "
            + PREFIX_ID + "REFERENCE ID "
            + PREFIX_START + "PREFIX_EVENT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "001A "
            + PREFIX_START + "01/11/19 1800";

    public static final String MESSAGE_USAGE_RECURSIVELY = COMMAND_WORD + ": Adds duty shifts recursively "
            + " to the duty cha. \n"
            + "Parameters: "
            + PREFIX_ID + "REFERENCE ID "
            + PREFIX_START + "PREFIX_EVENT "
            + "[" + PREFIX_RECURSIVE + "PREFIX_RECURSIVE w/m/y] "
            + "[" + PREFIX_RECURSIVE_TIMES + "PREFIX_RECURSIVE_TIMES]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "001A "
            + PREFIX_START + "01/11/19 1800 "
            + PREFIX_RECURSIVE + "m "
            + PREFIX_RECURSIVE_TIMES + "2\n";

    public static final String MESSAGE_ADD_APPOINTMENT_SUCCESS = "Duty shift added: %1$s";
    public static final String MESSAGE_SUCCESS_RECURSIVE = "%1$s recursive duty shifts were added";
    public static final String MESSAGE_CANCEL_APPOINTMENTS_CONSTRAINTS
            = "Must indicate at least 1 shift to add";

    private final Event toAdd;
    private final List<Event> eventList;

    /**
     * Creates an AddAppCommand to add the specified {@code Event}
     */
    public AddDutyShiftCommand(Event toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.eventList = null;
    }

    /**
     * Creates an AddAppCommand to add the specified {@code Events}
     */
    public AddDutyShiftCommand(List<Event> eventList) {
        requireNonNull(eventList);
        checkArgument(eventList.size() > 0, MESSAGE_CANCEL_APPOINTMENTS_CONSTRAINTS);
        this.toAdd = null;
        this.eventList = eventList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (eventList == null) {
                model.scheduleAppointment(toAdd);
                model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(toAdd.getPersonId()));
                return new CommandResult(String.format(MESSAGE_ADD_APPOINTMENT_SUCCESS, toAdd));

            }
            model.scheduleAppointments(eventList);
            model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(eventList.get(0).getPersonId()));
            return new CommandResult(String.format(MESSAGE_SUCCESS_RECURSIVE, eventList.size()));

        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDutyShiftCommand // instanceof handles nulls
                && toAdd.equals(((AddDutyShiftCommand) other).toAdd));
    }
}
