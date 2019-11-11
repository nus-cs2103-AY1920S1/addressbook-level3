package seedu.address.testutil;

import seedu.address.model.finance.Money;
import seedu.address.model.finance.Spending;
import seedu.address.model.project.Time;

import java.text.ParseException;

public class SpendingBuilder {

    private Money spending;
    private Time time;
    private String description;

    public static final String DEFAULT_TIME = "19/09/2019 1900";
    public static final String DEFAULT_DESCRIPTION = "booked UCC Theatre";
    public static final String DEFAULT_AMOUNT = "200";

    public SpendingBuilder() throws ParseException {
        spending = new Money(DEFAULT_AMOUNT);
        time = new Time(DEFAULT_TIME);
        description = DEFAULT_DESCRIPTION;
    }

    public SpendingBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SpendingBuilder withTime(Time time) {
        this.time = time;
        return this;
    }

    public SpendingBuilder withSpending(Money amount) {
        this.spending = amount;
        return this;
    }

    public Spending build() {
        return new Spending(spending, time, description);
    }
}
