package seedu.ichifund.testutil;

import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.Transaction;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_AMOUNT = "20.45";
    public static final String DEFAULT_DESCRIPTION = "Spiderman";
    public static final String DEFAULT_CATEGORY = "Entertainment";
    public static final String DEFAULT_DAY = "10";
    public static final String DEFAULT_MONTH = "1";
    public static final String DEFAULT_YEAR = "2019";
    public static final String DEFAULT_TRANSACTION_TYPE = "exp";

    private Description description;
    private Amount amount;
    private Category category;
    private Date date;
    private TransactionType transactionType;

    public TransactionBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        category = new Category(DEFAULT_CATEGORY);
        date = new DateBuilder().build();
        transactionType = new TransactionType(DEFAULT_TRANSACTION_TYPE);
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        description = transactionToCopy.getDescription();
        amount = transactionToCopy.getAmount();
        category = transactionToCopy.getCategory();
        date = transactionToCopy.getDate();
        transactionType = transactionToCopy.getTransactionType();
    }

    /**
     * Sets the {@code Description} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code TransactionType} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withTransactionType(String transactionType) {
        this.transactionType = new TransactionType(transactionType);
        return this;
    }

    public Transaction build() {
        return new Transaction(description, amount, category, date, transactionType);
    }

}
