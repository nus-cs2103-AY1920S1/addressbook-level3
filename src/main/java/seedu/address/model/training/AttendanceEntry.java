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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof AttendanceEntry) {
            AttendanceEntry casted = (AttendanceEntry) other;
            return this.person.equals(casted.getPerson()) && this.isPresent.equals(casted.getIsPresent());
        }
        return false;
    }
}
