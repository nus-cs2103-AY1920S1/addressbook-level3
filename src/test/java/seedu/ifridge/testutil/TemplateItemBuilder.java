package seedu.ifridge.testutil;

import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;

/**
 * A utility class to help with building TemplateItem objects.
 */
public class TemplateItemBuilder {
    public static final String DEFAULT_NAME = "Minced Pork";
    public static final String DEFAULT_AMOUNT = "300g";

    private Name name;
    private Amount amount;

    public TemplateItemBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TemplateItemBuilder(TemplateItem itemToCopy) {
        name = itemToCopy.getName();
        amount = itemToCopy.getAmount();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public TemplateItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public TemplateItemBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    public TemplateItem build() {
        return new TemplateItem(name, amount);
    }

}
