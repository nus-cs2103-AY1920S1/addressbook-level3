//@@author SakuraBlossom
package seedu.address.logic.commands.duties;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.exceptions.InvalidEventScheduleChangeException;
import seedu.address.model.events.predicates.EventMatchesRefIdPredicate;

/**
 * cancel a duty shift(s) for a staff member.
 */
public class CancelDutyShiftCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "cancelshift";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels shift from the duty roster. "
            + "Parameters: INDEX (positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_CANCEL_SHIFT_SUCCESS = "The duty shift for [%1$s] %2$s was cancelled:\n%3$s";
    public static final String MESSAGE_CANCEL_SHIFTS_SUCCESS = "%1$s reoccurring duty shifts for [%2$s] %3$s "
            + "were cancelled:\n%4$s";
    public static final String MESSAGE_CANCEL_SHIFTS_CONSTRAINTS = "Must indicate at least 1 shift to delete";

    private final Event toDelete;
    private final List<Event> eventList;


    public CancelDutyShiftCommand(Event toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
        this.eventList = null;
    }

    public CancelDutyShiftCommand(List<Event> eventList) {
        requireNonNull(eventList);
        checkArgument(eventList.size() > 0, MESSAGE_CANCEL_SHIFTS_CONSTRAINTS);
        this.toDelete = null;
        this.eventList = eventList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (eventList == null) {
                checkHasEvent(model, toDelete);
                model.deleteDutyShifts(toDelete);
                model.updateFilteredDutyShiftList(new EventMatchesRefIdPredicate(toDelete.getPersonId()));
                return new CommandResult(String.format(
                        MESSAGE_CANCEL_SHIFT_SUCCESS,
                        toDelete.getPersonId(),
                        toDelete.getPersonName(),
                        toDelete.getEventTiming()));
            }

            model.deleteDutyShifts(eventList);
            model.updateFilteredDutyShiftList(new EventMatchesRefIdPredicate(eventList.get(0).getPersonId()));
            return new CommandResult(String.format(
                    MESSAGE_CANCEL_SHIFTS_SUCCESS,
                    eventList.size(),
                    eventList.get(0).getPersonId(),
                    eventList.get(0).getPersonName(),
                    eventList.stream()
                            .map(e -> e.getEventTiming().toString()).collect(Collectors.joining("\n"))));

        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    //@@author SakuraBlossom

    /**
     * Checks if the given {@code eventToDelete} exist in the duty roster book.
     */
    private void checkHasEvent(Model model, Event eventToDelete) throws CommandException {
        if (!model.hasExactDutyShift(eventToDelete)) {
            throw new CommandException(String.format(Messages.MESSAGE_EVENT_NOT_FOUND, eventToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelDutyShiftCommand // instanceof handles nulls
                && toDelete.equals(((CancelDutyShiftCommand) other).toDelete));
    }
}
