package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.model.events.ContainsKeywordsPredicate;
import seedu.address.model.events.Event;

public class AckAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "ackappt";
    private Event appointment;
    private final ContainsKeywordsPredicate predicate;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ack a appointment to the address book. [make sure you call appointments first] "
            + "Parameters: "
            + "Integer " + "index number \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 ";

    public static final String MESSAGE_SUCCESS = "this appointmeent has been acked: %1$s";
    public static final String MESSAGE_NOTING_ACK = "Make sure [appointments] first and there are appointments to be acked";
    public static final String MESSAGE_IDX_TOO_LARGE = "the idx is too large, it is not exist in appointments list";
    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful! Appointment '%1$s' has been removed.";
    public static final String MESSAGE_UNDO_ADD_ERROR = "Could not undo the addition of appointment: %1$s";

    /**
     * Creates an AckAppCommand to add the specified {@code Person}
     */
    public AckAppCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AppointmentsCommand appList = new AppointmentsCommand(predicate);
        appList.execute(model);

        appointment = model.ackEvent(model.getFilteredEventList());
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
