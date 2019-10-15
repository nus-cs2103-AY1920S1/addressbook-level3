package seedu.ichifund.testutil;

import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * A utility class to help with building Repeater objects.
 */
public class RepeaterBuilder {

    public static final String DEFAULT_DESCRIPTION = "Phone bills";
    public static final String DEFAULT_AMOUNT = "42.06";
    public static final String DEFAULT_CATEGORY = "utilities";
    public static final String DEFAULT_TRANSACTION_TYPE = "exp";
    public static final String DEFAULT_MONTH_START_OFFSET = "1";
    public static final String DEFAULT_MONTH_END_OFFSET = "";
    public static final String DEFAULT_START_DAY = "1";
    public static final String DEFAULT_START_MONTH = "1";
    public static final String DEFAULT_START_YEAR = "2019";
    public static final String DEFAULT_END_DAY = "31";
    public static final String DEFAULT_END_MONTH = "12";
    public static final String DEFAULT_END_YEAR = "2019";

    private Description description;
    private Amount amount;
    private Category category;
    private TransactionType transactionType;
    private MonthOffset monthStartOffset;
    private MonthOffset monthEndOffset;
    private Date startDate;
    private Date endDate;

    public RepeaterBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        category = new Category(DEFAULT_CATEGORY);
        transactionType = new TransactionType(DEFAULT_TRANSACTION_TYPE);
        monthStartOffset = new MonthOffset(DEFAULT_MONTH_START_OFFSET);
        monthEndOffset = new MonthOffset(DEFAULT_MONTH_END_OFFSET);
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
     * Initializes the RepeaterBuilder with the data of {@code repeaterToCopy}.
     */
    public RepeaterBuilder(Repeater repeaterToCopy) {
        description = repeaterToCopy.getDescription();
        amount = repeaterToCopy.getAmount();
        category = repeaterToCopy.getCategory();
        monthStartOffset = repeaterToCopy.getMonthStartOffset();
        monthEndOffset = repeaterToCopy.getMonthEndOffset();
    }

    /**
     * Sets the {@code Description} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Builds the repeater.
     */
    public Repeater build() {
        return new Repeater(description, amount, category, transactionType,
                monthStartOffset, monthEndOffset, startDate, endDate);
    }

}
