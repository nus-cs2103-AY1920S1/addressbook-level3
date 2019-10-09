package seedu.address.testutil;

import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;
import seedu.address.model.events.Timing;
import seedu.address.model.person.parameters.PatientReferenceId;

/**
 * A utility class to help with building Person objects.
 */
public class EventBuilder {

    public static final String DEFAULT_REFERENCE_ID = "1234567A";
    public static final DateTime DEFAULT_STARTIMING = DateTime.tryParseSimpleDateFormat("01/11/2019 1800");
    public static final DateTime DEFAULT_ENDTIMING = DateTime.tryParseSimpleDateFormat("01/11/2019 1900");

    private ReferenceId id;
    private Timing timing;

    public EventBuilder() {
        id = new PatientReferenceId(DEFAULT_REFERENCE_ID);
        timing = new Timing(DEFAULT_STARTIMING, DEFAULT_ENDTIMING);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public EventBuilder(Event personToCopy) {
        id = personToCopy.getPersonId();
        timing = personToCopy.getEventTiming();
    }

    /**
     * Sets the {@code PatientReferenceId} of the {@code Person} that we are building.
     */
    public EventBuilder withId(String id) {
        this.id = new PatientReferenceId(id);
        return this;
    }

    public Event build() {
        return new Event(id, timing, new Status("APPROVED"));
    }

}
