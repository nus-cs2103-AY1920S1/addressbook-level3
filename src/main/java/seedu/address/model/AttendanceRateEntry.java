package seedu.address.model;

import seedu.address.model.person.Person;

/**
 * Wrapper for attendance record that will be displayed in the Calendar. Each entry has a person and a string that
 * indicates a person's attendance rate.
 */
public class AttendanceRateEntry {
    private Person person;
    private String attendanceRateString;

    public AttendanceRateEntry(Person person, String attendanceRateString) {
        this.person = person;
        this.attendanceRateString = attendanceRateString;
    }

    public Person getPerson() {
        return person;
    }

    public String getAttendanceRateString() {
        return attendanceRateString;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof AttendanceRateEntry) {
            AttendanceRateEntry casted = (AttendanceRateEntry) other;
            return this.person.equals(casted.getPerson())
                    && this.attendanceRateString.equals(casted.getAttendanceRateString());
        }
        return false;
    }
}

