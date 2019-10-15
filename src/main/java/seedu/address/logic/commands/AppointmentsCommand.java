package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.NonActionableCommand;
import seedu.address.model.Model;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.*;


/**
 * Finds and lists all events in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class AppointmentsCommand extends NonActionableCommand {

    public static final String COMMAND_WORD = "appointments";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose reference Id contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Optional parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " 001A";

//    private final ContainsKeywordsPredicate predicate;
    private final ReferenceId referenceId;

//    public AppointmentsCommand(ContainsKeywordsPredicate predicate) {
//        this.predicate = predicate;
//    }

    public AppointmentsCommand(ReferenceId referenceId) {
        this.referenceId = referenceId;
    }

    public AppointmentsCommand() {
        this.referenceId = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        autoMissEvent(model.getFilteredEventList(), model);
        if(referenceId==null){
            model.updateFilteredEventList();
        }else{
            //todo on tuesday
            //extra condition to make sure all event is also be APPROVED
            model.updateFilteredEventList(referenceId);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    /**
     * checks all the appointments that before the current time and then make them as missed.
     *
     * @param filteredEventList which is the eventList contains the referenceId
     */
    private void autoMissEvent(ObservableList<Event> filteredEventList, Model model) {
        for (Event ev : filteredEventList) {
            Timing evTiming = ev.getEventTiming();
            Date current = new Date();
            if (!ev.getStatus().equals(new Status(Status.AppointmentStatuses.SETTLED))
                    && evTiming.getEndTime().getTime().before(current)) {
                Event newAppt = new Appointment(ev.getPersonId(), ev.getEventTiming(), new Status(Status.AppointmentStatuses.MISSED));
                model.setEvent(ev, newAppt);
            }
        }
    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof AppointmentsCommand // instanceof handles nulls
//                && predicate.equals(((AppointmentsCommand) other).predicate)); // state check
//    }
}
