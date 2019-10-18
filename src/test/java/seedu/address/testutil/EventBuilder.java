package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDateTimeMap;
import seedu.address.model.event.EventManpowerAllocatedList;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Party";
    public static final String DEFAULT_VENUE = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE = "11/11/2019";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private EventName name;
    private EventDate startDate;
    private EventDate endDate;
    private EventVenue venue;
    private EventManpowerNeeded manpowerNeeded;
    private EventManpowerAllocatedList manpowerAllocatedList;
    private EventDateTimeMap eventDateTimeMap;
    private Set<Tag> tags = new HashSet<>();

    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
        startDate = new EventDate(LocalDate.parse(DEFAULT_DATE, FORMATTER));
        endDate = new EventDate(LocalDate.parse(DEFAULT_DATE, FORMATTER));
        venue = new EventVenue(DEFAULT_VENUE);
        manpowerNeeded = new EventManpowerNeeded("5");
        manpowerAllocatedList = new EventManpowerAllocatedList();
        eventDateTimeMap = new EventDateTimeMap();
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    /*public EventBuilder(Event personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /*
     * Sets the {@code Name} of the {@code Person} that we are building.

    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }
    */
    public Event build() {
        return new Event(name, venue, manpowerNeeded, startDate, endDate, tags);
    }


}
