package seedu.address.model.person;

import seedu.address.model.person.exceptions.SchedulingException;
import seedu.address.model.tag.Tag;

import java.util.Set;

/**
 * Represents a Driver available for work.
 * Gurantees: details are present and not null, field values are validated, id is immutable.
 */
public class Driver extends Person {

    //Identity fields
    private final int id;

    //data fields
    static int IDCOUNT = 1;
    private Schedule schedule;

    /**
     * Every field must be present and not null.
     */
    public Driver (Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        schedule = new Schedule();
        id = IDCOUNT;
        IDCOUNT++;
    }

    public int getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
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
