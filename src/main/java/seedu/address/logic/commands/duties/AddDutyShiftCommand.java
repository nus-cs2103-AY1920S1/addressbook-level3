//@@author woon17
package seedu.address.logic.commands.duties;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REOCCURRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REOCCURRING_TIMES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.exceptions.InvalidEventScheduleChangeException;
import seedu.address.model.events.predicates.EventMatchesRefIdPredicate;
import seedu.address.model.util.SampleAppointmentDataUtil;


/**
 * Adds a person to the address book.
 */
public class AddDutyShiftCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "newshift";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds reoccurring duty shifts"
            + " to the duty roster. \n"
            + "Parameters: "
            + PREFIX_ID + "REFERENCE_ID "
            + PREFIX_START + "PREFIX_START "
            + PREFIX_END + "PREFIX_END "
            + "[" + PREFIX_REOCCURRING + "PREFIX_REOCCURRING w/m/y] "
            + "[" + PREFIX_REOCCURRING_TIMES + "PREFIX_REOCCURRING_TIMES]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "W0000001A "
            + PREFIX_START + SampleAppointmentDataUtil.ONE_MONTH_LATER_MORNING.toString() + " "
            + PREFIX_END + SampleAppointmentDataUtil.ONE_MONTH_LATER_EVENING.toString() + " "
            + PREFIX_REOCCURRING + "m "
            + PREFIX_REOCCURRING_TIMES + "2\n";

    public static final String MESSAGE_ADD_SHIFT_SUCCESS = "Duty shift for %1$s [%2$s] were added:\n%3$s";
    public static final String MESSAGE_SUCCESS_REOCCURRING = "%1$s reoccurring duty shifts for [%2$s] "
            + "%3$s were added:\n%4$s";
    public static final String MESSAGE_CANCEL_SHIFTS_CONSTRAINTS = "Must indicate at least 1 shift to add";

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
        checkArgument(eventList.size() > 0, MESSAGE_CANCEL_SHIFTS_CONSTRAINTS);
        this.toAdd = null;
        this.eventList = eventList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setTabListing(OmniPanelTab.DUTY_SHIFT_TAB);
        try {
            if (eventList == null) {
                model.scheduleDutyShift(toAdd);
                model.updateFilteredDutyShiftList(new EventMatchesRefIdPredicate(toAdd.getPersonId()));
                return new CommandResult(String.format(
                        MESSAGE_ADD_SHIFT_SUCCESS,
                        toAdd.getPersonId(),
                        toAdd.getPersonName(),
                        toAdd.getEventTiming()));

            }
            model.scheduleDutyShift(eventList);
            model.updateFilteredDutyShiftList(new EventMatchesRefIdPredicate(eventList.get(0).getPersonId()));
            return new CommandResult(String.format(
                    MESSAGE_SUCCESS_REOCCURRING,
                    eventList.size(),
                    eventList.get(0).getPersonId(),
                    eventList.get(0).getPersonName(),
                    eventList.stream()
                            .map(e -> e.getEventTiming().toString()).collect(Collectors.joining("\n"))));

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
