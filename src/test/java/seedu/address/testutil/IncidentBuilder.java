package seedu.address.testutil;

import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.vehicle.District;

/**
 * A utility class to help with the building of Incident objects.
 */
public class IncidentBuilder {
    public static final String DEFAULT_DISTRICT = "1";
    public static final String DEFAULT_CALLER = "85355255";
    public static final String DEFAULT_DATETIME = "01/01/2019 20:00";
    public static final String DEFAULT_DESC = "This is an incident description.";

    private District district;
    private IncidentDateTime dateTime;
    private Description desc;
    private CallerNumber caller;

    public IncidentBuilder() {
        district = new District(Integer.parseInt(DEFAULT_DISTRICT));
        dateTime = new IncidentDateTime(DEFAULT_DATETIME);
        desc = new Description(DEFAULT_DESC);
        caller = new CallerNumber(DEFAULT_CALLER);
    }

    /**
     * Initializes the IncidentBuilder with the data of {@code IncidentToCopy}.
     */
    public IncidentBuilder(Incident incidentToCopy) {
        district = incidentToCopy.getDistrict();
        dateTime = incidentToCopy.getDateTime();
        desc = incidentToCopy.getDesc();
        caller = incidentToCopy.getCallerNumber();

    }

    /**
     * Sets the {@code District} of the {@code Incident} that we are building.
     */
    public IncidentBuilder withDistrict(String district) {
        this.district = new District(Integer.parseInt(district));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Incident} that we are building.
     */
    public IncidentBuilder withDescription(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code IncidentDateTime} of the {@code Incident} that we are building.
     */
    public IncidentBuilder withDateTime(String dateTime) {
        this.dateTime = new IncidentDateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code CallerNumber} of the {@code Incident} that we are building.
     */
    public IncidentBuilder withCaller(String caller) {
        this.caller = new CallerNumber(caller);
        return this;
    }


    public Incident build() {
        return new Incident(district, dateTime, caller, desc);
    }
}
