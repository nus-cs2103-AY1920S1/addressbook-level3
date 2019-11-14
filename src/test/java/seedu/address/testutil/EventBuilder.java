/*
@@author shihaoyap
 */

package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 *  A utility class to help with building Event objects.
 */

public class EventBuilder {

    public static final String DEFAULT_NAME = "Musical";
    public static final String DEFAULT_VENUE = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_MANPOWER_NEEDED = "5";
    public static final String DEFAULT_START_DATE = "11/11/2019";
    public static final String DEFAULT_END_DATE = "12/11/2019";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private EventName name;
    private EventVenue venue;
    private EventManpowerNeeded manpowerNeeded;
    private EventDate startDate;
    private EventDate endDate;
    private Set<Tag> tags;

    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
        venue = new EventVenue(DEFAULT_VENUE);
        manpowerNeeded = new EventManpowerNeeded(DEFAULT_MANPOWER_NEEDED);
        startDate = new EventDate(LocalDate.parse(DEFAULT_START_DATE, FORMATTER));
        endDate = new EventDate(LocalDate.parse(DEFAULT_END_DATE, FORMATTER));
        tags = new HashSet<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        venue = eventToCopy.getVenue();
        manpowerNeeded = eventToCopy.getManpowerNeeded();
        startDate = eventToCopy.getStartDate();
        endDate = eventToCopy.getEndDate();
        tags = new HashSet<>(eventToCopy.getTags());
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new EventName(name);
        return this;
    }

    /**
     * Sets the {@code EventVenue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = new EventVenue(venue);
        return this;
    }

    /**
     * Sets the {@code EventManpowerNeeded} of the {@code Event} that we are building.
     */
    public EventBuilder withManpowerNeeded(String num) {
        this.manpowerNeeded = new EventManpowerNeeded(num);
        return this;
    }

    /**
     * Sets the {@code EventStartDate} of the {@code Event} that we are building.
     */
    public EventBuilder withStartDate(LocalDate start) {
        this.startDate = new EventDate(start);
        return this;
    }

    /**
     * Sets the {@code EventEndDate} of the {@code Event} that we are building.
     */
    public EventBuilder withEndDate(LocalDate end) {
        this.endDate = new EventDate(end);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * creates an draft Event Objects and returns it
     * @return Event Object
     */
    public Event build() {
        return new Event(name, venue, manpowerNeeded, startDate, endDate, tags);
    }
}
