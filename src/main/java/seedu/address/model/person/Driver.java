package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.EventTime;
import seedu.address.model.person.exceptions.SchedulingException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Driver available for work.
 * Gurantees: details are present and not null, field values are validated, id is immutable.
 */
public class Driver extends Person {

    public static final String MESSAGE_INVALID_ID = "Invalid driver ID.";

    //Identity fields
    private static int idCount = 1;
    private final int id;

    //data fields
    private Schedule schedule;

    /**
     * Every field must be present and not null.
     */
    public Driver(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        schedule = new Schedule();
        id = idCount;
        idCount++;
    }

    public int getId() {
        return id;
    }

    public int getIdCount() {
        return idCount;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String suggestTime(EventTime eventTime) {
        return this.schedule.getSchedulingSuggestion(eventTime);
    }

    public void assign(EventTime eventTime) throws SchedulingException {
        this.schedule.add(eventTime);
    }


    /**
     * Returns a string representation of the driver, with identity fields visible to the user.
     *
     * @return string representation of driver
     */
    @Override
    public String toString() {
        StringBuilder driverBuilder = new StringBuilder();
        driverBuilder.append(" Driver stats: \n")
                .append(" id: ")
                .append(getId())
                .append(super.toString());
        return driverBuilder.toString();
    }

}
