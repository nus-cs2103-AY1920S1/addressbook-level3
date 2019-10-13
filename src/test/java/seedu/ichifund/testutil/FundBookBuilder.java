package seedu.ichifund.testutil;

import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.person.Person;

/**
 * A utility class to help with building FundBook objects.
 * Example usage: <br>
 *     {@code FundBook ab = new FundBookBuilder().withPerson("John", "Doe").build();}
 */
public class FundBookBuilder {

    private FundBook fundBook;

    public FundBookBuilder() {
        fundBook = new FundBook();
    }

    public FundBookBuilder(FundBook fundBook) {
        this.fundBook = fundBook;
    }

    /**
     * Adds a new {@code Person} to the {@code FundBook} that we are building.
     */
    public FundBookBuilder withPerson(Person person) {
        fundBook.addPerson(person);
        return this;
    }

    public FundBook build() {
        return fundBook;
    }
}
