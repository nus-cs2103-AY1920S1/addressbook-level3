//@@author woon17
package seedu.address.logic.commands.appointments;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.NonActionableCommand;
import seedu.address.model.Model;
import seedu.address.model.events.predicates.EventsMissedPredicate;

/**
 * mark a appointment's status as MISSED for a patient.
 */
public class MissApptCommand extends NonActionableCommand {
    public static final String COMMAND_WORD = "missappt";

    public static final String MESSAGE_MISSED_EVENT_LISTED_OVERVIEW = "%1$d missed appointment need to settle!";
    public static final String MESSAGE_MISSED_EVENTS_LISTED_OVERVIEW = "%1$d missed appointments need to settle!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all the missed appointment "
            + "and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD;

    public MissApptCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.setTabListing(OmniPanelTab.APPOINTMENTS_TAB);
        model.updateFilteredAppointmentList(new EventsMissedPredicate());
        int size = model.getFilteredAppointmentList().size();
        String displayMess;

        if (size <= 1) {
            displayMess = MESSAGE_MISSED_EVENT_LISTED_OVERVIEW;
        } else {
            displayMess = MESSAGE_MISSED_EVENTS_LISTED_OVERVIEW;
        }
        return new CommandResult(
                String.format(displayMess, size));
    }

}
