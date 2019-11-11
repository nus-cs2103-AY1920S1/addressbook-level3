package seedu.address.testutil;

import seedu.address.model.commons.Amount;
import seedu.address.model.commons.Currency;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.expense.Expense;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Expense objects with raw date.
 */
public class RawExpenseBuilder {

    public static final String DEFAULT_NAME = "textbook";
    public static final String DEFAULT_AMOUNT = "23.50";
    public static final String DEFAULT_CURRENCY = "SGD";
    public static final String DEFAULT_DATE = "13/10/2019";
    public static final String DEFAULT_TAG = "education";

    private Name name;
    private Amount amount;
    private Currency currency;
    private Date date;
    private Tag tag;

    public RawExpenseBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        currency = new Currency(DEFAULT_CURRENCY);
        date = new Date(DEFAULT_DATE, false);
        tag = new Tag(DEFAULT_TAG);
    }

    /**
     * Initializes the RawExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public RawExpenseBuilder(Expense expenseToCopy) {
        name = expenseToCopy.getName();
        amount = expenseToCopy.getAmount();
        currency = expenseToCopy.getCurrency();
        date = expenseToCopy.getDate();
        tag = expenseToCopy.getTag();
    }

    /**
     * Sets the {@code Name} of the {@code Expense} that we are building.
     */
    public RawExpenseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tag} into a {@code Set<Tag>} and set it to the {@code Expense} that we are building.
     */
    public RawExpenseBuilder withTag(String tag) {
        this.tag = new Tag(tag);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Expense} that we are building.
     */
    public RawExpenseBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Currency} of the {@code Expense} that we are building.
     */
    public RawExpenseBuilder withCurrency(String currency) {
        this.currency = new Currency(currency);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Expense} that we are building.
     */
    public RawExpenseBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Expense build() {
        return new Expense(name, amount, currency, date, tag);
    }

}
