package seedu.ichifund.testutil;

import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.transaction.Transaction;

/**
 * A utility class to help with building FundBook objects.
 * Example usage: <br>
 *     {@code FundBook ab = new FundBookBuilder().withTransaction("John", "Doe").build();}
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
     * Adds a new {@code Transaction} to the {@code FundBook} that we are building.
     */
    public FundBookBuilder withTransaction(Transaction transaction) {
        fundBook.addTransaction(transaction);
        return this;
    }

    public FundBook build() {
        return fundBook;
    }
}
