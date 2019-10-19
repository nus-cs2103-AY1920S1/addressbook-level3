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
 *  A utility class to help with building Event objects.
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
        name = personToCopy.getEmployeeName();
        phone = personToCopy.getEmployeePhone();
        email = personToCopy.getEmployeeEmail();
        address = personToCopy.getEmployeeAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /*
     * Sets the {@code EmployeeName} of the {@code Employee} that we are building.

    public PersonBuilder withName(String name) {
        this.name = new EmployeeName(name);
        return this;
    }

    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public PersonBuilder withAddress(String address) {
        this.address = new EmployeeAddress(address);
        return this;
    }

    public PersonBuilder withPhone(String phone) {
        this.phone = new EmployeePhone(phone);
        return this;
    }

    public PersonBuilder withEmail(String email) {
        this.email = new EmployeeEmail(email);
        return this;
    }
    */
    public Event build() {
        return new Event(name, venue, manpowerNeeded, startDate, endDate, tags);
    }


}
