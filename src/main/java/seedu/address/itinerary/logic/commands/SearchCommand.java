package seedu.address.itinerary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.itinerary.logic.parser.CliSyntax.*;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Search and list events from the itinerary based on conditions.
 */
public class SearchCommand extends Command<Model> {
    public static final String COMMAND_WORD = "search";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": TravEzy helps you to search and give you a life.\n"
            + "Returns a new list which reflects all the events that matches.\n"
            + "Parameters: search "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]"
            + "[" + PREFIX_TAG +  "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "15032015 "
            + PREFIX_LOCATION + "Singapore\n"
            + "Do remember if you indicate tag, choose the tag from the Priority dropdown bar";
    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "Here are the events that matches the details. ( ͡° ͜ʖ ͡°)";

    private final SearchEventDescriptor searchEventDescriptor;

    public SearchCommand(SearchEventDescriptor searchEventDescriptor) {
        requireNonNull(searchEventDescriptor);

        this.searchEventDescriptor = new SearchEventDescriptor(searchEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredEventList(this::filterEvents);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

    /**
     * Filter the event list in the itinerary based on the search condition.
     * @param event traverse through each event in the event list in itinerary.
     * @return filtered list with the event which matches the search condition.
     */
    private boolean filterEvents(Event event) {
        if (searchEventDescriptor.getTitle().filter(title -> !title.equals(event.getTitle())).isPresent()) {
            return false;
        }

        if (searchEventDescriptor.getDate().filter(date -> !date.equals(event.getDate())).isPresent()) {
            return false;
        }

        if (searchEventDescriptor.getTime().filter(time -> !time.equals(event.getTime())).isPresent()) {
            return false;
        }

        if (searchEventDescriptor.getLocation().filter(location -> !location.equals(event.getLocation())).isPresent()) {
            return false;
        }

        if (searchEventDescriptor.getTag().filter(tag -> !tag.equals(event.getTag())).isPresent()) {
            return false;
        }

        return searchEventDescriptor.getDescription().filter(description ->
                !description.equals(event.getDesc())).isEmpty();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class SearchEventDescriptor {
        private Title title;
        private Date date;
        private Time time;
        private Location location;
        private Description description;
        private Tag tag;

        public SearchEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        SearchEventDescriptor(SearchEventDescriptor toCopy) {
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
            if (!(other instanceof SearchEventDescriptor)) {
                return false;
            }

            // state check
            SearchEventDescriptor e = (SearchEventDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getLocation().equals(e.getLocation())
                    && getDescription().equals(e.getDescription());
        }
    }
}
