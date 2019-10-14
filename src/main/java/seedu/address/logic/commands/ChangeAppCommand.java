package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * change a appointment's timing for a patient.
 */
public class ChangeAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "changeAppt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change the appointment date "
            + "by the index number used in the displayed patient's list. "
            + "Existing date will be overwritten by the input date.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_START + "PREFIX_EVENT "
            + PREFIX_END + "PREFIX_EVENT \n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_START + "01/11/19 1800 "
            + PREFIX_END + "01/11/19 1900";

    private final Index apptIdx;
    private Event oldAppt;
    private Event newAppt;
    private Timing timing;

    public static final String MESSAGE_CANCEL_APPOINTMENT_SUCCESS = "Appointment cancelled: %1$s";
    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful! Person '%1$s' has been removed.";
    public static final String MESSAGE_UNDO_ADD_ERROR = "Could not undo the addition of person: %1$s";

    public ChangeAppCommand(Index index, Timing timing){
        this.apptIdx = index;
        this.timing = timing;
        oldAppt = null;
        newAppt = null;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Event> filterEventList = model.getFilteredEventList();

        if (apptIdx.getZeroBased() >= filterEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        if (!model.isPatientList()) {
            throw new CommandException(Messages.MESSAGE_NOT_PATIENTLIST);
        }

        oldAppt = filterEventList.get(apptIdx.getZeroBased());
        newAppt = new Appointment(oldAppt.getPersonId(), timing, new Status());

        if(model.hasEvent(newAppt)){
            throw new CommandException(Messages.MESSAGE_DUPLICATE_CHANGE);
        }

        model.setEvent(oldAppt, newAppt);

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(oldAppt);
        model.updateFilteredEventList(predicate);

        return new CommandResult(String.format(MESSAGE_CANCEL_APPOINTMENT_SUCCESS, newAppt));
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(oldAppt)) {
            throw new CommandException(String.format(MESSAGE_UNDO_ADD_ERROR, oldAppt));
        }

        model.setEvent(newAppt, oldAppt);


        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(oldAppt);
        model.updateFilteredEventList(predicate);

        return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, oldAppt));
    }

}
