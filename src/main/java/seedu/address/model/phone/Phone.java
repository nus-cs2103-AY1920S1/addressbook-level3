package seedu.address.model.phone;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.tag.Tag;

/**
 * Represents a Phone in the SML.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Phone implements Cloneable {

    // Identity fields
    private final UUID id;

    // Data fields
    private final PhoneName phoneName;
    private final Brand brand;
    private final Capacity capacity;
    private final Colour colour;
    private final Cost cost;
    private final Set<Tag> tags = new HashSet<>();

    public Phone(PhoneName phoneName, Brand brand, Capacity capacity, Colour colour, Cost cost,
                 Set<Tag> tags) {
        requireAllNonNull(phoneName, brand, capacity, colour, cost, tags);
        this.id = UUID.randomUUID();
        this.phoneName = phoneName;
        this.brand = brand;
        this.capacity = capacity;
        this.colour = colour;
        this.cost = cost;
        this.tags.addAll(tags);
    }

    private Phone(UUID id, PhoneName phoneName, Brand brand, Capacity capacity, Colour colour, Cost cost,
                 Set<Tag> tags) {
        requireAllNonNull(id, phoneName, brand, capacity, colour, cost, tags);
        this.id = id;
        this.phoneName = phoneName;
        this.brand = brand;
        this.capacity = capacity;
        this.colour = colour;
        this.cost = cost;
        this.tags.addAll(tags);
    }

    public UUID getId() {
        return id;
    }

    public PhoneName getPhoneName() {
        return phoneName;
    }

    public Brand getBrand() {
        return brand;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public Colour getColour() {
        return colour;
    }

    public Cost getCost() {
        return cost;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both phones have the same identity fields.
     */
    public boolean isSamePhone(Phone otherPhone) {
        if (otherPhone == this) {
            return true;
        }

        return otherPhone != null
                && otherPhone.getId().equals(getId());
    }

    /**
     * Returns true if both phones have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return otherPhone.getPhoneName().equals(getPhoneName())
                && otherPhone.getBrand().equals((getBrand()))
                && otherPhone.getCapacity().equals((getCapacity()))
                && otherPhone.getColour().equals((getColour()))
                && otherPhone.getCost().equals(getCost())
                && otherPhone.getTags().equals((getTags()));
    }

    @Override
    public Object clone() {
        Phone clone = new Phone(this.id, (PhoneName) this.phoneName.clone(), (Brand) this.brand.clone(), this.capacity,
                (Colour) this.colour.clone(), (Cost) this.cost.clone(), this.getTags());
        return clone;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, phoneName, brand, capacity, colour, cost, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" # ")
                .append(getId())
                .append(" Name: ")
                .append(getPhoneName())
                .append(" Brand: ")
                .append(getBrand())
                .append(" Capacity: ")
                .append(getCapacity())
                .append(" Color: ")
                .append(getColour())
                .append(" Cost: ")
                .append(getCost())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
