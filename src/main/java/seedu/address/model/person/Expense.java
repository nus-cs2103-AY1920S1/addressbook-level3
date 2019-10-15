package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

public class Expense extends Entry {

    private static final String ENTRYTYPE = "Expense";

    public Expense(Description desc, Time time, Amount amount, Set<Tag> tags) {
        super(desc,time,amount,tags);
    }

    public String getType() {
        return this.ENTRYTYPE;
    }


}
