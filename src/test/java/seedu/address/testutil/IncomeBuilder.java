package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.claim.Amount;
import seedu.address.model.claim.Description;
import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.income.Income;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Helps with building of income object
 */
public class IncomeBuilder {

    public static final String DEFAULT_DESCRIPTION = "Shirt sales";
    public static final String DEFAULT_AMOUNT = "100.07";
    public static final String DEFAULT_DATE = "11-11-2019";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";

    private Description description;
    private Amount amount;
    private Date date;
    private Name name;
    private Phone phone;
    private Set<Tag> tags;

    public IncomeBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the IncomeBuilder with the data of {@code incomeToCopy}.
     */
    public IncomeBuilder(Income incomeToCopy) {
        description = incomeToCopy.getDescription();
        amount = incomeToCopy.getAmount();
        date = incomeToCopy.getDate();
        name = incomeToCopy.getName();
        phone = incomeToCopy.getPhone();
        tags = new HashSet<>(incomeToCopy.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code FinSec} that we are building.
     */
    public IncomeBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code FinSec} that we are building.
     */
    public IncomeBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code FinSec} that we are building.
     */
    public IncomeBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code FinSec} that we are building.
     */
    public IncomeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code FinSec} that we are building.
     */
    public IncomeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code FinSec} that we are building.
     */
    public IncomeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    public Income build() {
        return new Income(description, amount, date, name, phone, tags);
    }

}
