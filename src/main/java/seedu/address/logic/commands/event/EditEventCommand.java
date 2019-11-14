/*
@@author DivineDX
 */

package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_BIG_RANGE;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_START_AFTER_END;
import static seedu.address.commons.core.Messages.MESSAGE_EVENTS_DUPLICATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDateTimeMap;
import seedu.address.model.event.EventManpowerAllocatedList;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;
import seedu.address.ui.MainWindow;

/**
 * Edits the details of an existing {@code Event} in the EventBook.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "edit_ev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_NAME + "EVENT NAME] "
            + "[" + PREFIX_EVENT_VENUE + "VENUE] "
            + "[" + PREFIX_EVENT_MANPOWER_NEEDED + "MANPOWER NEEDED] "
            + "[" + PREFIX_EVENT_START_DATE + "START DATE] "
            + "[" + PREFIX_EVENT_END_DATE + "END DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_NAME + "Drawing Competition "
            + PREFIX_EVENT_VENUE + "Utown Student Plaza";

    private static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    private static final String MESSAGE_INVALID_MANPOWER_COUNT_NEEDED = "Invalid Manpower Needed input."
            + " You may not state a number that is below the current number of allocated employees. \n"
            + "Free some employees before executing this command again!";
    private static final String MESSAGE_EVENT_HAS_ALLOCATED_MANPOWER =
            "Free all allocated manpower before editing Event Date";


    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index               of the event in the filtered event list to edit
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

        if (MainWindow.isFinanceTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EVENT_LIST);
        }

        List<Event> lastShownList = MainWindow.getCurrentEventList(model);
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);
        EventDate editedStartDate = editedEvent.getStartDate();
        EventDate editedEndDate = editedEvent.getEndDate();

        //Event has Manpower Allocated, EventDate is edited, prompts user to deallocate before Editing Dates.
        if (!eventToEdit.isManpowerAllocatedEmpty()
                && !(eventToEdit.getStartDate().equals(editedStartDate)
                && eventToEdit.getEndDate().equals(editedEndDate))) {
            throw new CommandException(MESSAGE_EVENT_HAS_ALLOCATED_MANPOWER);
        }

        //start date not before end date
        if (editedStartDate.compareTo(editedEndDate) > 0) {
            throw new CommandException(String.format(MESSAGE_DATE_START_AFTER_END, editedStartDate, editedEndDate));
        }

        if (editedEvent.getManpowerNeeded().value < editedEvent.getCurrentManpowerCount()) {
            throw new CommandException(MESSAGE_INVALID_MANPOWER_COUNT_NEEDED);
        }

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_EVENTS_DUPLICATE);
        }

        long dateDifference = editedStartDate.dateDifference(editedEndDate);

        if (dateDifference > 90) {
            throw new CommandException(String.format(
                    MESSAGE_DATE_BIG_RANGE, editedStartDate, editedEndDate, dateDifference));
        }

        model.setEvent(eventToEdit, editedEvent);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     * <p>
     * Event will be edited differently based on the fields that changed
     * If only Name, Venue, Manpower Needed or tags are changed, Event will inherit the ManpowerList & DateTimeMap.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventName updatedEventName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        EventVenue updatedEventVenue = editEventDescriptor.getVenue().orElse(eventToEdit.getVenue());
        EventManpowerNeeded updatedManpowerNeeded = editEventDescriptor.getManpowerNeeded()
                .orElse(eventToEdit.getManpowerNeeded());
        EventDate updatedStartDate = editEventDescriptor.getStartDate().orElse(eventToEdit.getStartDate());
        EventDate updatedEndDate = editEventDescriptor.getEndDate().orElse(eventToEdit.getEndDate());
        Set<Tag> updatedTags = editEventDescriptor.getTags().orElse(eventToEdit.getTags());
        EventManpowerAllocatedList updatedManpowerAllocatedList = eventToEdit.getManpowerAllocatedList();
        EventDateTimeMap updatedDateTimeMap = new EventDateTimeMap(eventToEdit.getEventDateTimeMap());

        if (updatedStartDate != eventToEdit.getStartDate()
                || updatedEndDate != eventToEdit.getEndDate()) {
            updatedDateTimeMap.flushEventDates(updatedStartDate, updatedEndDate);
        }

        return new Event(updatedEventName, updatedEventVenue,
                updatedManpowerNeeded, updatedStartDate,
                updatedEndDate, updatedManpowerAllocatedList, updatedDateTimeMap, updatedTags);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
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
        //Data Fields
        private EventName name;
        private EventVenue venue;
        private EventManpowerNeeded manpowerNeeded;
        private EventDate startDate;
        private EventDate endDate;
        private Set<Tag> tags;

        public EditEventDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        EditEventDescriptor(EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setVenue(toCopy.venue);
            setManpowerNeeded(toCopy.manpowerNeeded);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, venue, manpowerNeeded,
                    startDate, endDate, tags);
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

        public void setStartDate(EventDate startDate) {
            this.startDate = startDate;
        }

        public Optional<EventDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(EventDate endDate) {
            this.endDate = endDate;
        }

        public Optional<EventDate> getEndDate() {
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

            return getName().equals(e.getName())
                    && getVenue().equals(e.getVenue())
                    && getManpowerNeeded().equals(e.getManpowerNeeded())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getTags().equals(e.getTags());
        }
    }
}
