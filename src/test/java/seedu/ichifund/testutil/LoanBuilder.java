package seedu.ichifund.testutil;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.loan.Name;

/**
 * A utility class to help with building Loan objects.
 */
public class LoanBuilder {

    public static final String DEFAULT_ID = "0";
    public static final String DEFAULT_NAME = "Bob the Builder";
    public static final String DEFAULT_DESCRIPTION = "Home Loan";
    public static final String DEFAULT_AMOUNT = "420.69";
    public static final String DEFAULT_START_DAY = "12";
    public static final String DEFAULT_START_MONTH = "12";
    public static final String DEFAULT_START_YEAR = "2019";
    public static final String DEFAULT_END_DAY = "1";
    public static final String DEFAULT_END_MONTH = "11";
    public static final String DEFAULT_END_YEAR = "2024";

    private LoanId loanId;
    private Name name;
    private Description description;
    private Amount amount;
    private Date startDate;
    private Date endDate;

    public LoanBuilder() {
        loanId = new LoanId(DEFAULT_ID);
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        startDate = new Date(
                new Day(DEFAULT_START_DAY),
                new Month(DEFAULT_START_MONTH),
                new Year(DEFAULT_START_YEAR));
        endDate = new Date(
                new Day(DEFAULT_END_DAY),
                new Month(DEFAULT_END_MONTH),
                new Year(DEFAULT_END_YEAR));
    }

    /**
     * Initializes the LoanBuilder with the data of {@code loanToCopy}.
     */
    public LoanBuilder(Loan loanToCopy) {
        loanId = loanToCopy.getLoanId();
        name = loanToCopy.getName();
        description = loanToCopy.getDescription();
        amount = loanToCopy.getAmount();
        startDate = loanToCopy.getStartDate();
        endDate = loanToCopy.getEndDate();
    }

    /**
     * Sets the {@code Description} of the {@code Loan} that we are building.
     */
    public LoanBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Loan} that we are building.
     */
    public LoanBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Loan} that we are building.
     */
    public LoanBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Builds the loan.
     */
    public Loan build() {
        return new Loan(loanId, amount, name, startDate, endDate, description);
    }

}
