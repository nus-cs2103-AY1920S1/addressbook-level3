package seedu.address.itinerary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.itinerary.logic.parser.CliSyntax;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.itinerary.model.exceptions.ItineraryException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Edits the details of an existing event in the itinerary.
 */
@SuppressWarnings("MalformedFormatString")
public class EditCommand extends Command<Model> {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_TITLE + "TITLE] "
            + "[" + CliSyntax.PREFIX_DATE + "DATE] "
            + "[" + CliSyntax.PREFIX_TIME + "TIME] "
            + "[" + CliSyntax.PREFIX_LOCATION + "LOCATION] "
            + "[" + CliSyntax.PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + CliSyntax.PREFIX_TAG + "] "
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_DATE + "15032015 "
            + CliSyntax.PREFIX_LOCATION + "Singapore";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided. ╚(•⌂•)╝";
    private static final String MESSAGE_EDIT_EVENT_SUCCESS = "Updated successfully! :D\n" + "HAND, TrazEzy~";
    private static final String MESSAGE_DUPLICATE_EVENT = "This event exists in the itinerary event list.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getSortedEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        try {
            model.setEvent(eventToEdit, editedEvent);
        } catch (ItineraryException e) {
            e.getMessage();
        }
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Title updatedTitle = editEventDescriptor.getTitle().orElse(eventToEdit.getTitle());
        Date updatedDate = editEventDescriptor.getDate().orElse(eventToEdit.getDate());
        Time updatedTime = editEventDescriptor.getTime().orElse(eventToEdit.getTime());
        Location updatedLocation = editEventDescriptor.getLocation().orElse(eventToEdit.getLocation());
        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDesc());
        Tag updatedTag = editEventDescriptor.getTag().orElse(eventToEdit.getTag());

        Event event = new Event(updatedTitle, updatedDate, updatedLocation,
                updatedDescription, updatedTime, updatedTag);

        if (eventToEdit.getIsDone()) {
            event.markIsDone();
        }

        return event;
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
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private Title title;
        private Date date;
        private Time time;
        private Location location;
        private Description description;
        private Tag tag;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        EditEventDescriptor(EditEventDescriptor toCopy) {
            setTitle(toCopy.title);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setLocation(toCopy.location);
            setDescription(toCopy.description);
            setTag(toCopy.tag);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, date, time, location, description, tag);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription () {
            return Optional.ofNullable(description);
        }

        public void setTag(Tag tag) {
            this.tag = tag;
        }

        public Optional<Tag> getTag () {
            return Optional.ofNullable(tag);
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

            return getTitle().equals(e.getTitle())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getLocation().equals(e.getLocation())
                    && getDescription().equals(e.getDescription());
        }
    }
}
