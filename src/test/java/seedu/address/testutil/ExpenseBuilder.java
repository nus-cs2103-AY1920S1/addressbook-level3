package seedu.address.testutil;

import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {

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

    public ExpenseBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        currency = new Currency(DEFAULT_CURRENCY);
        date = new Date(DEFAULT_DATE);
        tag = new Tag(DEFAULT_TAG);
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        name = expenseToCopy.getName();
        amount = expenseToCopy.getAmount();
        currency = expenseToCopy.getCurrency();
        date = expenseToCopy.getDate();
        tag = expenseToCopy.getTag();
    }

    /**
     * Sets the {@code Name} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tag} into a {@code Tag} and set it to the {@code Expense} that we are building.
     */
    public ExpenseBuilder withTag(String tag) {
        this.tag = new Tag(tag);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Currency} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withCurrency(String currency) {
        this.currency = new Currency(currency);
        return this;
    }

    /**
     * Sets empty {@code Date} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDate() {
        this.date = new Date();
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Expense build() {
        return new Expense(name, amount, currency, date, tag);
    }
}
