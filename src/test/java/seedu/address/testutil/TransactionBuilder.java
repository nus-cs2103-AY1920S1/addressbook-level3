package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.OutTransaction;
import seedu.address.model.util.Date;
import seedu.address.model.util.SampleDataUtil;

/**
 * A helper class for building Transactions for purposes of testing
 */
public class TransactionBuilder {

    public static final String DEFAULT_AMOUNT = "100";
    public static final String DEFAULT_DATE = "10102019";
    public static final String DEFAULT_CATEGORY = "category";
    public static final String DEFAULT_DESCRIPTION = "milk";

    private Description description;
    private Amount amount;
    private Date date;
    private Set<Category> categories;

    public TransactionBuilder() {
        amount = new Amount(Double.parseDouble(DEFAULT_AMOUNT));
        date = new Date(DEFAULT_DATE);
        description = new Description(DEFAULT_DESCRIPTION);
        categories = new HashSet<>();
        categories.add(new Category(DEFAULT_CATEGORY));
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(BankAccountOperation transactionToCopy) {
        amount = transactionToCopy.getAmount();
        date = transactionToCopy.getDate();
        description = transactionToCopy.getDescription();
        categories = new HashSet<>(transactionToCopy.getCategories());
    }

    /**
     * Sets the {@code amount} of the {@code Transaction} that we are building.
     */
    public seedu.address.testutil.TransactionBuilder withAmount(String amount) {
        this.amount = new Amount(Double.parseDouble(amount));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>}
     * and set it to the {@code Transaction} that we are building.
     */
    public seedu.address.testutil.TransactionBuilder withCategories(String... categories) {
        this.categories = SampleDataUtil.getCategorySet(categories);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Transaction} that we are building.
     */
    public seedu.address.testutil.TransactionBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Transaction} that we are building.
     */
    public seedu.address.testutil.TransactionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Builds BankOperation of InTransaction or OutTransaction
     */
    public BankAccountOperation build() {
        if (amount.isNegative()) {
            return new OutTransaction(amount, date, description, categories);
        } else {
            return new InTransaction(amount, date, description, categories);
        }
    }
}
