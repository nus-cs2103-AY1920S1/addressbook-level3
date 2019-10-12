package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_HOURS_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventEndDate;
import seedu.address.model.event.EventHoursNeeded;
import seedu.address.model.event.EventId;
import seedu.address.model.event.EventManpowerAllocatedList;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventStartDate;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing event in the address book.
 */
public class EditEventCommand extends EventRelatedCommand {

    public static final String COMMAND_WORD = "editEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_NAME + "EVENT NAME] "
            + "[" + PREFIX_EVENT_VENUE + "VENUE] "
            + "[" + PREFIX_EVENT_HOURS_NEEDED + "HOURS NEEDED] "
            + "[" + PREFIX_EVENT_MANPOWER_NEEDED + "MANPOWER NEEDED] "
            + "[" + PREFIX_EVENT_START_DATE + "START DATE] "
            + "[" + PREFIX_EVENT_END_DATE + "END DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_NAME + "Drawing Competition "
            + PREFIX_EVENT_VENUE + "Utown Student Plaza";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventId updatedEventId = editEventDescriptor.getEventId().orElse(eventToEdit.getEventId());
        EventName updatedEventName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        EventVenue updatedEventVenue = editEventDescriptor.getVenue().orElse(eventToEdit.getVenue());
        EventHoursNeeded updatedHoursNeeded = editEventDescriptor.getHoursNeeded()
                .orElse(eventToEdit.getHoursNeeded());
        EventManpowerNeeded updatedManpowerNeeded = editEventDescriptor.getManpowerNeeded()
                .orElse(eventToEdit.getManpowerNeeded());
        EventStartDate updatedStartDate = editEventDescriptor.getStartDate().orElse(eventToEdit.getStartDate());
        EventEndDate updatedEndDate = editEventDescriptor.getEndDate().orElse(eventToEdit.getEndDate());
        Set<Tag> updatedTags = editEventDescriptor.getTags().orElse(eventToEdit.getTags());

        return new Event(updatedEventId, updatedEventName, updatedEventVenue,
                updatedHoursNeeded, updatedManpowerNeeded, updatedStartDate,
                updatedEndDate, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditEventDescriptor {
        //Identity Fields
        private EventId eventId;

        //Data Fields
        private EventName name;
        private EventVenue venue;
        private EventManpowerNeeded manpowerNeeded;
        private EventHoursNeeded hoursNeeded;
        private EventStartDate startDate;
        private EventEndDate endDate;
        private EventManpowerAllocatedList manpowerAllocatedList;
        private Set<Tag> tags = new HashSet<>();

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setEventId(toCopy.eventId);
            setName(toCopy.name);
            setVenue(toCopy.venue);
            setManpowerNeeded(toCopy.manpowerNeeded);
            setHoursNeeded(toCopy.hoursNeeded);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventId, name, venue, manpowerNeeded,
                    hoursNeeded, startDate, endDate, tags);
        }

        public void setEventId(EventId id) {
            this.eventId = id;
        }

        public Optional<EventId> getEventId() {
            return Optional.ofNullable(eventId);
        }

        public void setName(EventName name) {
            this.name = name;
        }

        public Optional<EventName> getName() {
            return Optional.ofNullable(name);
        }

        public void setVenue(EventVenue venue) {
            this.venue = venue;
        }

        public Optional<EventVenue> getVenue() {
            return Optional.ofNullable(venue);
        }

        public void setManpowerNeeded(EventManpowerNeeded manpowerNeeded) {
            this.manpowerNeeded = manpowerNeeded;
        }

        public Optional<EventManpowerNeeded> getManpowerNeeded() {
            return Optional.ofNullable(manpowerNeeded);
        }

        public void setHoursNeeded(EventHoursNeeded hoursNeeded) {
            this.hoursNeeded = hoursNeeded;
        }

        public Optional<EventHoursNeeded> getHoursNeeded() {
            return Optional.ofNullable(hoursNeeded);
        }

        public void setStartDate(EventStartDate startDate) {
            this.startDate = startDate;
        }

        public Optional<EventStartDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(EventEndDate endDate) {
            this.endDate = endDate;
        }

        public Optional<EventEndDate> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getEventId().equals(e.getEventId())
                    && getName().equals(e.getName())
                    && getVenue().equals(e.getVenue())
                    && getManpowerNeeded().equals(e.getManpowerNeeded())
                    && getHoursNeeded().equals(e.getHoursNeeded())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getTags().equals(e.getTags());
        }
    }
}
