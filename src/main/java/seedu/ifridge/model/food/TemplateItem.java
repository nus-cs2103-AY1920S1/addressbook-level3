package seedu.ifridge.model.food;

import static seedu.ifridge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;


/**
 * Represents a TemplateList item in the template list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TemplateItem implements Comparable<TemplateItem> {

    // Identity fields
    private final Name name;
    private final Amount amount;

    /**
     * Every field must be present and not null.
     */
    public TemplateItem(Name name, Amount amount) {
        requireAllNonNull(name);
        this.name = name;
        this.amount = amount;
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    /**
     * Returns true if both food items are of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two food items.
     */
    public boolean isSameFood(TemplateItem otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" ");
        builder.append(getAmount());
        return builder.toString();
    }


    @Override
    public int compareTo(TemplateItem other) {
        String thisName = this.getName().toString();
        String otherName = this.getName().toString();

        return thisName.compareTo(otherName);
    }
}
