package seedu.jarvis.model.financeTracker;

/**
 * Creates an instance to be used for tracking when a payment has been made to a person.
 * todo this is created when 1. the user adds it himself 2. when the user marks the tab as paid
 */
public class PersonPaid {
    private String personName;

    public PersonPaid(String name) {
        this.personName = name;
    }

    public String getPersonName() {
        return this.personName;
    }

    @Override
    public String toString() {
        return this.personName;
    }
}
