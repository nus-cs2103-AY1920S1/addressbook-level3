package seedu.address.model.training;

import seedu.address.model.person.Person;

/**
 * Wrapper for attendance record that will be displayed in the Calendar. Each entry has a person and a boolean and
 * indicates whether a person attended the training.
 */
public class AttendanceEntry {
    private Person person;
    private Boolean isPresent;

    public AttendanceEntry(Person person, Boolean isPresent) {
        this.person = person;
        this.isPresent = isPresent;
    }

    public Person getPerson() {
        return person;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }
}
