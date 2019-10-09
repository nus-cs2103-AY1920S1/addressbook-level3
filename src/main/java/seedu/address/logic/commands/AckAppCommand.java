package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.common.Command;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AckAppCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.ContainsKeywordsPredicate;
import seedu.address.model.events.Event;
import seedu.address.ui.UiManager;

import java.util.Arrays;
import java.util.logging.Logger;

public class AckAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "ackappt";
    private Event appointment;
    private final ReferenceId referenceId;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ack a appointment to the address book. "
            + "the specified keywords (case-insensitive).\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " 001A";

    public static final String MESSAGE_SUCCESS = "this appointmeent has been acked: %1$s";
    public static final String MESSAGE_NOTING_ACK = "there is no appointment under this patient.";
    public static final String MESSAGE_DUPLICATE_ACKED = "the upcoming appointment has been acked already.";
    public static final String MESSAGE_INVAILD_REFERENCEID = "the refernceId is invalid.";
    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful! Appointment '%1$s' has been removed.";
    public static final String MESSAGE_UNDO_ADD_ERROR = "Could not undo the addition of appointment: %1$s";

    /**
     * Creates an AckAppCommand to add the specified {@code Person}
     */
    public AckAppCommand(ReferenceId referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        AppointmentsCommand appList = new AppointmentsCommand(new ContainsKeywordsPredicate(Arrays.asList(referenceId.toString().split("\\s+"))));
        appList.execute(model);
        ObservableList<Event> filterEventList = model.getFilteredEventList();

        if (!model.hasPerson(referenceId)) {
            throw new CommandException(MESSAGE_INVAILD_REFERENCEID);
        }else if (filterEventList.size() == 0) {
            throw new CommandException(MESSAGE_NOTING_ACK);
        } else if (filterEventList.get(0).getStatus().isAcked()) {
            throw new CommandException(MESSAGE_DUPLICATE_ACKED);
        }

        appointment = model.getFilteredEventList().get(0);

        model.ackEvent(appointment);

        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasEvent(appointment)) {
            throw new CommandException(String.format(MESSAGE_UNDO_ADD_ERROR, appointment));
        }

        model.deleteEvent(appointment);
        return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, appointment));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AckAppCommand // instanceof handles nulls
                && appointment.equals(((AckAppCommand) other).appointment));
    }

}
