package seedu.address.itinerary.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.itinerary.model.Event.*;
import seedu.address.itinerary.model.Exceptions.ItineraryException;
import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_TITLE;

public class EditCommand extends Command {
    /** {@code Predicate} that always evaluate to true */
    public Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "15032015 "
            + PREFIX_LOCATION + "Singapore";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Updated successfully! :D\n" + "HAND, TrazEzy~";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event exists in the address book.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editPersonDescriptor details to edit the event with
     */
    public EditCommand(Index index, EditEventDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

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
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
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

        Event event = new Event(updatedTitle, updatedDate, updatedLocation, updatedDescription, updatedTime);

        if (eventToEdit.getIsDone() == true) {
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
     * corresponding field value of the person.
     */
    public static class EditEventDescriptor {
        private Title title;
        private Date date;
        private Time time;
        private Location location;
        private Description description;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setTitle(toCopy.title);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setLocation(toCopy.location);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, date, time, location, description);
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
