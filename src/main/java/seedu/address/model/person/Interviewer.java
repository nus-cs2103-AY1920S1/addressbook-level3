package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.model.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Represents an Interviewer in the scheduler.
 */
public class Interviewer extends Person {

    private final List<Schedule> schedules = new ArrayList<>();
    private final Department department;

    /**
     * Every field must be present and not null.
     */
    public Interviewer(Department department, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.department = department;
    }
    // TODO: Implementation
}
