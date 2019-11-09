package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_EVENT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DATETIME_RANGE;
import static seedu.address.commons.util.EventUtil.isSameVEvent;
import static seedu.address.commons.util.EventUtil.vEventToString;
import static seedu.address.commons.util.EventUtil.validateStartEndDateTime;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.descriptive.Categories;
import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the events details in the events record.
 */
public class EventEditCommand extends EventCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Edits a event\n"
        + "Parameters(All are optional):\n"
        + "eventName/ [EVENTNAME]\n"
        + "startDateTime/ [STARTDATETIME]\n"
        + "endDateTime/ [ENDDATETIME]\n"
        + "recur/ [DAILY/WEEKLY/NONE]\n"
        + "color/ [0 - 23]\n"
        + "Example: event 6 eventName/cs2100 lecture startDateTime/2019-10-21T14:00 "
        + "endDateTime/2019-10-21T15:00 recur/none color/1";
    public static final String NO_FIELDS_CHANGED = "At least one field to edit must be provided.";
    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited event: %1$s";

    private final EditVEventDescriptor editVEventDescriptor;
    private final Index index;

    /**
     * Creates a event edit command.
     *
     * @param index                 Index of the event to be edited.
     * @param editVEventDescriptor Object used to edit the event which was specified.
     */
    public EventEditCommand(Index index, EditVEventDescriptor editVEventDescriptor) {
        this.index = index;
        this.editVEventDescriptor = editVEventDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (index.getZeroBased() >= model.getVEventList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (!editVEventDescriptor.isAnyFieldEdited()) {
            throw new CommandException(NO_FIELDS_CHANGED);
        }

        VEvent vEventObjectToEdit = model.getVEvent(index);
        VEvent editedVEvent;
        try {
            editedVEvent = createdEditedVEvent(vEventObjectToEdit, editVEventDescriptor);
        } catch (IllegalValueException ex) {
            throw new CommandException(ex.getMessage(), ex);
        }

        if (!isSameVEvent(editedVEvent, vEventObjectToEdit) && model.hasVEvent(editedVEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.setVEvent(index, editedVEvent);

        return new CommandResult(generateSuccessMessage(editedVEvent), CommandResultType.SHOW_SCHEDULE);
    }

    /**
     * Generates a command execution success message.
     *
     * @param vEvent that has been editted.
     */
    private String generateSuccessMessage(VEvent vEvent) {
        return String.format(MESSAGE_EDIT_EVENT_SUCCESS, vEventToString(vEvent));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventEditCommand)) {
            return false;
        }

        //state check
        EventEditCommand e = (EventEditCommand) other;
        return index.equals(e.index)
            && editVEventDescriptor.equals(e.editVEventDescriptor);
    }

    /**
     * Creates and returns a {@code VEvent} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    private static VEvent createdEditedVEvent(VEvent vEventToEdit, EditVEventDescriptor editVEventDescriptor)
            throws IllegalValueException {
        assert vEventToEdit != null;

        String updatedEventName = editVEventDescriptor.getEventName().orElse(vEventToEdit.getSummary().getValue());
        LocalDateTime updatedStartDateTime = editVEventDescriptor.getStartDateTime()
                .orElse(LocalDateTime.from(vEventToEdit.getDateTimeStart().getValue()));
        LocalDateTime updatedEndDateTime = editVEventDescriptor.getEndDateTime()
                .orElse(LocalDateTime.from(vEventToEdit.getDateTimeEnd().getValue()));
        List<Categories> updatedColorCategory = editVEventDescriptor.getColorCategory()
                .orElse(vEventToEdit.getCategories());
        RecurrenceRule updatedRecurrenceRule = editVEventDescriptor.getRecurrenceRule()
                .orElse(vEventToEdit.getRecurrenceRule());
        String uniqueIdentifier = vEventToEdit.getUniqueIdentifier().getValue();

        if (!validateStartEndDateTime(updatedStartDateTime, updatedEndDateTime)) {
            throw new IllegalValueException(MESSAGE_INVALID_EVENT_DATETIME_RANGE);
        }

        return new VEvent().withRecurrenceRule(updatedRecurrenceRule).withCategories(updatedColorCategory)
                .withDateTimeEnd(updatedEndDateTime).withDateTimeStart(updatedStartDateTime)
                .withUniqueIdentifier(uniqueIdentifier).withSummary(updatedEventName);
    }


    /**
     * Stores the details to edit the VEvent with. Each non-empty field value will replace the
     * corresponding field value of the VEvent.
     */
    public static class EditVEventDescriptor {
        private String eventName;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private RecurrenceRule recurrenceRule;
        private List<Categories> colorCategory;

        public EditVEventDescriptor() {

        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditVEventDescriptor(EventEditCommand.EditVEventDescriptor toCopy) {
            setEventName(toCopy.eventName);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventName, startDateTime, endDateTime, recurrenceRule, colorCategory);
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public Optional<String> getEventName() {
            return Optional.ofNullable(eventName);
        }

        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<LocalDateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<LocalDateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
            this.recurrenceRule = recurrenceRule;
        }

        public Optional<RecurrenceRule> getRecurrenceRule() {
            return Optional.ofNullable(recurrenceRule);
        }

        public void setColorCategory(List<Categories> colorCategory) {
            this.colorCategory = colorCategory;
        }

        public Optional<List<Categories> > getColorCategory() {
            return Optional.ofNullable(colorCategory);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EventEditCommand.EditVEventDescriptor)) {
                return false;
            }

            // state check
            EditVEventDescriptor e = (EditVEventDescriptor) other;

            return getEventName().equals(e.getEventName())
                    && getColorCategory().equals(e.getColorCategory())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getColorCategory().equals(e.getColorCategory());
        }
    }
}
