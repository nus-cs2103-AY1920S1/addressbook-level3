package seedu.moolah.model.menu;

import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;

/**
 * Represents a menu item in MooLah.
 */
public class MenuItem {

    public static final String MESSAGE_CONSTRAINTS =
            "Only supported menu items are allowed. Refer to User Guide for the complete list.";

    private Description description;
    private Price price;

    public MenuItem(Description description, Price price) {
        requireAllNonNull(description, price);
        this.description = description;
        this.price = price;
    }

    public Description getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof MenuItem)) {
            return false;
        }

        MenuItem other = (MenuItem) obj;

        return description.equals(other.description)
                && price.equals(other.price);
    }
}
