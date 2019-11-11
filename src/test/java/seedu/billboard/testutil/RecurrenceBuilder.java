package seedu.billboard.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.ExpenseList;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.recurrence.Recurrence;
import seedu.billboard.model.tag.Tag;

/**
 * A utility class to help with building Recurrence objects.
 */
public class RecurrenceBuilder {

    public static final String DEFAULT_NAME = "bill";
    public static final String DEFAULT_DESCRIPTION = "pay bills";
    public static final String DEFAULT_AMOUNT = "20";
    public static final String DEFAULT_DATE = "1/1/2019";
    public static final String DEFAULT_INTERVAL = "month";
    public static final int DEFAULT_ITERATIONS = 5;

    private ExpenseList expenses;
    private Name name;
    private Description description;
    private Amount amount;
    private CreatedDateTime created;
    private Set<Tag> tags;
    private DateInterval interval;
    private int iterations;

    public RecurrenceBuilder() {
        expenses = null;
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        created = new CreatedDateTime(DEFAULT_DATE);
        tags = new HashSet<>();
        interval = DateInterval.valueOf(DEFAULT_INTERVAL.toUpperCase());
        iterations = DEFAULT_ITERATIONS;
    }

    /**
     * Initializes the RecurrenceBuilder with the data of {@code recurrenceToCopy}.
     */
    public RecurrenceBuilder(Recurrence recurrenceToCopy) {
        expenses = recurrenceToCopy.getExpenses();
        name = recurrenceToCopy.getName();
        description = recurrenceToCopy.getDescription();
        amount = recurrenceToCopy.getAmount();
        created = recurrenceToCopy.getCreated();
        tags = recurrenceToCopy.getTags();
        interval = recurrenceToCopy.getInterval();
    }

    /**
     * Sets the {@code Expenses} of the {@code Recurrence} that we are building.
     */
    public RecurrenceBuilder withExpenses(List<Expense> expenses, DateInterval interval) {
        this.expenses = new ExpenseList(expenses);
        this.interval = interval;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Recurrence} that we are building.
     */
    public RecurrenceBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Recurrence} that we are building.
     */
    public RecurrenceBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Recurrence} that we are building.
     */
    public RecurrenceBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Recurrence} that we are building.
     */
    public RecurrenceBuilder withDate(String date) {
        this.created = new CreatedDateTime(date);
        return this;
    }

    /**
     * Sets the {@code Iterations} of the {@code Recurrence} that we are building.
     */
    public RecurrenceBuilder withIterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    /**
     * Sets the {@code DateInterval} of the {@code Recurrence} that we are building.
     */
    public RecurrenceBuilder withInterval(String interval) {
        this.interval = DateInterval.valueOf(interval.toUpperCase());
        return this;
    }

    /**
     * Sets the {@code Tag} of the {@code Recurrence} that we are building.
     */
    public RecurrenceBuilder withTag(String validTagDinner) {
        this.tags = new HashSet<>(Arrays.asList(new Tag(validTagDinner)));
        return this;
    }

    /**
     * Build the {@code Recurrence} based on the parameters given.
     */
    public Recurrence build() {
        if (expenses != null) {
            return new Recurrence(expenses, interval);
        }

        Expense expense;
        ExpenseList expenses = new ExpenseList();

        for (int i = 0; i < iterations; i++) {
            expense = new Expense(
                    name.getClone(),
                    description.getClone(),
                    amount.getClone(),
                    created.plus(interval, i),
                    tags);
            expenses.add(expense);
        }
        return new Recurrence(expenses, interval);
    }
}
