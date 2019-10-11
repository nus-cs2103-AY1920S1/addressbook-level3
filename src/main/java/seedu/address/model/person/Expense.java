package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Expense.
 */
public class Expense extends Entry {

    private static final String ENTRY_TYPE = "Expense";
    private final Time time;

    public Expense(Description desc, Time time, Amount amount, Set<Tag> tags) {
        super(desc, amount, tags);
        this.time = time;
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }

    public Time getTime() {
        return this.time;
    }


}
