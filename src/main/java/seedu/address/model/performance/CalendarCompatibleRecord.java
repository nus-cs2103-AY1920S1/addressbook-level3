package seedu.address.model.performance;

import seedu.address.model.person.Person;

/**
 * Wrapper for performance record that will be displayed in the Calendar.
 * Differs from Record class in that the attributes are athlete and timing, instead of date and timing since
 * the date for a CalendarCompatibleRecord is fixed already.
 */
public class CalendarCompatibleRecord {

    private Person athlete;
    private String timing;

    public CalendarCompatibleRecord(Person athlete, String timing) {
        this.athlete = athlete;
        this.timing = timing;
    }

    public Person getAthlete() {
        return athlete;
    }

    public String getTiming() {
        return timing;
    }
}
