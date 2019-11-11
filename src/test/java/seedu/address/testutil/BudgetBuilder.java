package seedu.address.testutil;

import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Money;
import seedu.address.model.finance.Spending;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BudgetBuilder {
    public static final String DEFAULT_NAME = "Venue";
    public static final Money DEFAULT_AMOUNT = new Money("1000");
    public static final List<Spending> DEFAULT_SPENDINGS = new ArrayList<>();

    private String name;
    private Money amount;
    private List<Spending> spendings;

    public BudgetBuilder() throws ParseException {
        name = DEFAULT_NAME;
        amount = DEFAULT_AMOUNT;
        spendings = DEFAULT_SPENDINGS;
    }

    public BudgetBuilder withAmount(String amount) {
        this.amount = new Money(amount);
        return this;
    }

    public BudgetBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public BudgetBuilder withSpendings(List<Spending> spendings) {
        this.spendings = spendings;
        return this;
    }

    public Budget build() {
        return new Budget(name, amount, spendings);
    }

}
