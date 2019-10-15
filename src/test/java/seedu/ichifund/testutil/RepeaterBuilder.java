package seedu.ichifund.testutil;

import seedu.ichifund.model.transaction.Amount;
import seedu.ichifund.model.transaction.Description;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Category;

/**
 * A utility class to help with building Repeater objects.
 */
public class RepeaterBuilder {

    public static final String DEFAULT_DESCRIPTION = "Phone bills";
    public static final String DEFAULT_AMOUNT = "42.06";
    public static final String DEFAULT_CATEGORY = "utilities";
    public static final String DEFAULT_MONTH_START_OFFSET = "1";
    public static final String DEFAULT_MONTH_END_OFFSET = "";

    private Description description;
    private Amount amount;
    private Category category;
    private MonthOffset monthStartOffset;
    private MonthOffset monthEndOffset;

    public RepeaterBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        category = new Category(DEFAULT_CATEGORY);
        monthStartOffset = new MonthOffset(DEFAULT_MONTH_START_OFFSET);
        monthEndOffset = new MonthOffset(DEFAULT_MONTH_END_OFFSET);
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

    public Repeater build() {
        return new Repeater(description, amount, category, monthStartOffset, monthEndOffset);
    }

}
