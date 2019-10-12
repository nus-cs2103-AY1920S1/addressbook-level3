package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

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
 * A utility class to help with building Person objects.
 */
public class EventBuilder {

    public static final String DEFAULT_ID = "000";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_VENUE = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE = "11/11/2019";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private EventId id;
    private EventName name;
    private EventVenue venue;
    private EventManpowerNeeded manpowerNeeded;
    private EventHoursNeeded hoursNeeded;
    private EventStartDate startDate;
    private EventEndDate endDate;
    private EventManpowerAllocatedList manpowerAllocatedList;
    private Set<Tag> tags = new HashSet<>();

    public EventBuilder() {
        id = new EventId(DEFAULT_ID);
        name = new EventName(DEFAULT_NAME);
        venue = new EventVenue(DEFAULT_VENUE);
        manpowerNeeded = new EventManpowerNeeded("5");
        hoursNeeded = new EventHoursNeeded("5");
        startDate = new EventStartDate(LocalDate.parse(DEFAULT_DATE, FORMATTER));
        endDate = new EventEndDate(LocalDate.parse(DEFAULT_DATE, FORMATTER));
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
        return new Event(id, name, venue, hoursNeeded, manpowerNeeded, startDate, endDate, tags);
    }



}
