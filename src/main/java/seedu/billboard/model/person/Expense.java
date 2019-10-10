package seedu.billboard.model.person;

import java.util.Set;

import seedu.billboard.model.tag.Tag;

/**
 * Represents a Expense in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense extends Record {

    /**
     * Every field must be present and not null.
     */
    public Expense(Name name, Description description, Amount amount, Set<Tag> tags) {
        super(name, description, amount, tags);
    }

    public Amount getAmount() {
        return amount;
    }
}
