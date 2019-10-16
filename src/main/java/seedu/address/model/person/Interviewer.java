package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Interviewer in the scheduler.
 */
public class Interviewer extends Person {

    // Each availability is given as a string in this format DD/MM/YYYY HH:MM - HH:MM
    private final List<String> availabilities = new ArrayList<>();
    private final Department department;

    /**
     * Every field must be present and not null.
     */
    public Interviewer(Department department, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public List<String> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<String> availabilities) {
        this.availabilities.clear();
        this.availabilities.addAll(availabilities);
    }
}
