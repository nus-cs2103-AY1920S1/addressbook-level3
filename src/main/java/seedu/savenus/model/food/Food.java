package seedu.savenus.model.food;

import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_RESTRICTIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.savenus.model.tag.Tag;

/**
 * Represents a Food in the menu.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food {

    // Identity fields
    private final Name name;
    private final Price price;
    private final Description description;
    private final Category category;
    private final Location location;
    private final OpeningHours openingHours;
    private final Restrictions restrictions;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Food(Name name, Price price, Description description, Category category, Set<Tag> tags,
                    Location location, OpeningHours openingHours, Restrictions restrictions) {
        requireAllNonNull(name, price, description, category, tags, openingHours, restrictions);
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.tags.addAll(tags);
        this.location = location;
        this.openingHours = openingHours;
        this.restrictions = restrictions;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Location getLocation() {
        return location;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public Restrictions getRestrictions() {
        return restrictions;
    }

    public Field getField(String field) {
        switch(field) {
        case FIELD_NAME_NAME:
            return getName();

        case FIELD_NAME_PRICE:
            return getPrice();

        case FIELD_NAME_CATEGORY:
            return getCategory();

        case FIELD_NAME_DESCRIPTION:
            return getDescription();

        case FIELD_NAME_LOCATION:
            return getLocation();

        case FIELD_NAME_OPENING_HOURS:
            return getOpeningHours();

        case FIELD_NAME_RESTRICTIONS:
            return getRestrictions();

        default:
            return null;
        }
    }

    /**
     * Returns true if both foods of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two foods.
     */
    public boolean isSameFood(Food otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName())
                && otherFood.getPrice().equals(getPrice())
                && otherFood.getDescription().equals(getDescription())
                && otherFood.getCategory().equals(getCategory())
                && otherFood.getLocation().equals(getLocation())
                && otherFood.getOpeningHours().equals(getOpeningHours())
                && otherFood.getRestrictions().equals(getRestrictions());
    }

    /**
     * Returns true if both foods have the same identity and data fields.
     * This defines a stronger notion of equality between two foods.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Food)) {
            return false;
        }

        Food otherFood = (Food) other;
        return otherFood.getName().equals(getName())
                && otherFood.getPrice().equals(getPrice())
                && otherFood.getDescription().equals(getDescription())
                && otherFood.getTags().equals(getTags())
                && otherFood.getCategory().equals(getCategory())
                && otherFood.getLocation().equals(getLocation())
                && otherFood.getOpeningHours().equals(getOpeningHours())
                && otherFood.getRestrictions().equals(getRestrictions());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price, description, category, tags, openingHours, restrictions);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Price: $")
                .append(getPrice())
                .append(" Description: ")
                .append(getDescription())
                .append(" Category: ")
                .append(getCategory())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Location: ")
                .append(getLocation())
                .append(" Opening Hours: ")
                .append(getOpeningHours())
                .append(" Restrictions: ")
                .append(getRestrictions());
        return builder.toString();
    }

}
