package seedu.jarvis.model.financetracker;

/**
 * Creates an instance to be used for tracking when a payment has been made to a person.
 * todo this is created when 1. the user adds it himself 2. when the user marks the tab to others as paid
 */
public class PurchaseToPerson extends Purchase {
    private String personName;

    public PurchaseToPerson(String description, double value, String name) {
        super(description, value);
        this.personName = name;
    }

    public String getPersonName() {
        return this.personName;
    }

    @Override
    public String toString() {
        return super.getDescription() + " paid to " + this.personName + " (" + super.getMoneySpent() + ")";
    }
}
