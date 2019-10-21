package seedu.address.testutil;

import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Transaction;

/**
 * Builds a transaction with default attributes.
 */
public class TransactionBuilder {
    public static final int DEFAULT_ID = 1;
    public static final String DEFAULT_DATE = "24-Aug-2019";
    public static final String DEFAULT_DESCRIPTION = "poster printing";
    public static final String DEFAULT_CATEGORY = "marketing";
    public static final double DEFAULT_AMOUNT = 15.0;
    public static final boolean DEFAULT_IS_REIMBURSED = false;

    private String date;
    private String description;
    private String category;
    private double amount;
    private Person person;
    private int id;
    private boolean isReimbursed;

    /**
     * Initializes the TransactionBuilder with the data of {@code person}.
     */
    public TransactionBuilder(Person person) {
        this.date = DEFAULT_DATE;
        this.description = DEFAULT_DESCRIPTION;
        this.category = DEFAULT_CATEGORY;
        this.amount = DEFAULT_AMOUNT;
        this.person = person;
        this.id = DEFAULT_ID;
        this.isReimbursed = DEFAULT_IS_REIMBURSED;
    }

    /**
     * Sets the date of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the category of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withCategory(String cat) {
        this.category = cat;
        return this;
    }

    /**
     * Sets the description of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code Person} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Sets id of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets amount of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Builds the {@code Transaction}.
     */
    public Transaction build() {
        return new Transaction(date, description, category, amount, person, id, isReimbursed);
    }

}
