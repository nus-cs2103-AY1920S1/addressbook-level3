package seedu.ichifund.testutil;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * A utility class to help with building Repeater objects.
 */
public class RepeaterBuilder {

    public static final String DEFAULT_UNIQUE_ID = "";
    public static final String DEFAULT_DESCRIPTION = "Phone bills";
    public static final String DEFAULT_AMOUNT = "42.06";
    public static final String DEFAULT_CATEGORY = "utilities";
    public static final String DEFAULT_TRANSACTION_TYPE = "exp";
    public static final String DEFAULT_MONTH_START_OFFSET = "5";
    public static final String DEFAULT_MONTH_END_OFFSET = "-1";
    public static final String DEFAULT_START_MONTH = "1";
    public static final String DEFAULT_START_YEAR = "2019";
    public static final String DEFAULT_END_MONTH = "12";
    public static final String DEFAULT_END_YEAR = "2019";

    private RepeaterUniqueId uniqueId;
    private Description description;
    private Amount amount;
    private Category category;
    private TransactionType transactionType;
    private MonthOffset monthStartOffset;
    private MonthOffset monthEndOffset;
    private Date startDate;
    private Date endDate;

    public RepeaterBuilder() {
        uniqueId = new RepeaterUniqueId(DEFAULT_UNIQUE_ID);
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        category = new Category(DEFAULT_CATEGORY);
        transactionType = new TransactionType(DEFAULT_TRANSACTION_TYPE);
        monthStartOffset = new MonthOffset(DEFAULT_MONTH_START_OFFSET);
        monthEndOffset = new MonthOffset(DEFAULT_MONTH_END_OFFSET);
        startDate = new Date(
                new Day("1"),
                new Month(DEFAULT_START_MONTH),
                new Year(DEFAULT_START_YEAR));
        endDate = new Date(
                new Day("1"),
                new Month(DEFAULT_END_MONTH),
                new Year(DEFAULT_END_YEAR));
    }

    /**
     * Initializes the RepeaterBuilder with the data of {@code repeaterToCopy}.
     */
    public RepeaterBuilder(Repeater repeaterToCopy) {
        uniqueId = repeaterToCopy.getUniqueId();
        description = repeaterToCopy.getDescription();
        amount = repeaterToCopy.getAmount();
        category = repeaterToCopy.getCategory();
        transactionType = repeaterToCopy.getTransactionType();
        monthStartOffset = repeaterToCopy.getMonthStartOffset();
        monthEndOffset = repeaterToCopy.getMonthEndOffset();
        startDate = repeaterToCopy.getStartDate();
        endDate = repeaterToCopy.getEndDate();
    }

    /**
     * Sets the {@code RepeaterUniqueId} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withUniqueId(String uniqueId) {
        this.uniqueId = new RepeaterUniqueId(uniqueId);
        return this;
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
     * Sets the {@code Category} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    /**
     * Sets the {@code TransactionType} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withTransactionType(String transactionType) {
        this.transactionType = new TransactionType(transactionType);
        return this;
    }

    /**
     * Sets the {@code MonthStartOffset} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withMonthStartOffset(String monthStartOffset) {
        this.monthStartOffset = new MonthOffset(monthStartOffset);
        return this;
    }

    /**
     * Sets the {@code MonthEndOffset} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withMonthEndOffset(String monthEndOffset) {
        this.monthEndOffset = new MonthOffset(monthEndOffset);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code Repeater} that we are building.
     */
    public RepeaterBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Builds the repeater.
     */
    public Repeater build() {
        System.out.println(uniqueId.toString());
        System.out.println(description.toString());
        System.out.println(amount.toString());
        System.out.println(category.toString());
        System.out.println(transactionType.toString());
        System.out.println(monthStartOffset.toString());
        System.out.println(monthEndOffset.toString());
        System.out.println(startDate.toString());
        System.out.println(startDate.toString());
        return new Repeater(uniqueId, description, amount, category, transactionType,
                monthStartOffset, monthEndOffset, startDate, endDate);
    }

}
