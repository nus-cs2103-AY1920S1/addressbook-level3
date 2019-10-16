package seedu.address.testutil;

import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Transaction;

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
     * Initializes the PersonBuilder with the data of {@code person}.
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
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public TransactionBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public TransactionBuilder withCategory(String cat) {
        this.category = cat;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public TransactionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TransactionBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    public TransactionBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public TransactionBuilder withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Transaction build() {
        return new Transaction(date, description, category, amount, person, id, isReimbursed);
    }

}
