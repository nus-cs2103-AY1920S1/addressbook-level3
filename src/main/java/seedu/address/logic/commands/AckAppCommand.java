package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.Arrays;

import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.ContainsKeywordsPredicate;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;
import seedu.address.model.events.Timing;

/**
 * Acknowledge a person to the address book.
 */
public class AckAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "ackappt";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ack a appointment to the address book. "
            + "the specified keywords (case-insensitive).\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " 001A";

    public static final String MESSAGE_SUCCESS = "this appointmeent has been acked: %1$s";
    public static final String MESSAGE_NOTING_ACK = "there is no appointment under this patient.";
    public static final String MESSAGE_DUPLICATE_ACKED = "the upcoming appointment has been acked already.";

    private final ReferenceId referenceId;
    private final EditEventStatus editEventStatus;
    private Event eventToAck;
    private Event ackedEvent;

    /**
     * Creates an AckAppCommand to add the specified {@code Person}
     */
    public AckAppCommand(ReferenceId referenceId, EditEventStatus editEventStatus) {
        this.referenceId = referenceId;
        this.editEventStatus = new EditEventStatus(editEventStatus);
        eventToAck = null;
        ackedEvent = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String[] nameKeywords = referenceId.toString().split("\\s+");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        AppointmentsCommand appList = new AppointmentsCommand(predicate);
        appList.execute(model);
        ObservableList<Event> filterEventList = model.getFilteredEventList();


        if (!model.hasPerson(referenceId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVAILD_REFERENCE_ID, referenceId));
        } else if (filterEventList.size() == 0) {
            throw new CommandException(MESSAGE_NOTING_ACK);
        } else if (filterEventList.get(0).getStatus().isAcked()) {
            throw new CommandException(MESSAGE_DUPLICATE_ACKED);
        }

        if (eventToAck == null && ackedEvent == null) {
            eventToAck = filterEventList.get(0);
            ackedEvent = createEditedEvent(eventToAck, editEventStatus);
        }

        model.setEvent(eventToAck, ackedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, ackedEvent));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventStatus editEventStatus) {
        assert eventToEdit != null;

        ReferenceId updatedRefId = editEventStatus.getReferenceId().orElse(eventToEdit.getPersonId());
        Timing updatedTiming = editEventStatus.getTiming().orElse(eventToEdit.getEventTiming());
        Status updatedStatus = editEventStatus.getStatus().orElse(eventToEdit.getStatus());

        return new Event(updatedRefId, updatedTiming, updatedStatus);
    }

    /*
    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (eventToAck == null || eventToAck == null || !model.hasExactEvent(eventToAck)) {
            throw new CommandException(MESSAGE_NOT_ACKED);
        }

        if (eventToAck.equals(ackedEvent) || model.hasExactEvent(eventToAck)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACKED);
        }

        if (model.hasEvent(eventToAck) && !eventToAck.isSameEvent(ackedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACKED);
        }

        model.setEvent(ackedEvent, eventToAck);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, eventToAck));
    }*/

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AckAppCommand)) {
            return false;
        }

        AckAppCommand e = (AckAppCommand) other;
        return referenceId.equals(e.referenceId)
                && editEventStatus.equals(e.editEventStatus);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditEventStatus {
        private ReferenceId referenceId;
        private Timing timing;
        private Status status;

        public EditEventStatus() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventStatus(EditEventStatus toCopy) {
            setReferenceId(toCopy.referenceId);
            setTiming(toCopy.timing);
            ackStatus();
        }


        public void setReferenceId(ReferenceId referenceId) {
            this.referenceId = referenceId;
        }

        public Optional<ReferenceId> getReferenceId() {
            return Optional.ofNullable(referenceId);
        }

        public void setTiming(Timing timing) {
            this.timing = timing;
        }

        public Optional<Timing> getTiming() {
            return Optional.ofNullable(timing);
        }

        public void ackStatus() {
            this.status = new Status(Status.AppointmentStatuses.ACKNOWLEDGED);
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventStatus)) {
                return false;
            }

            // state check
            EditEventStatus e = (EditEventStatus) other;

            return getReferenceId().equals(getReferenceId())
                    && getTiming().equals(e.getTiming())
                    && getStatus().equals(e.getStatus());
        }
    }

}
