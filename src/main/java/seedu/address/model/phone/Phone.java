package seedu.address.model.phone;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.Identifiable;
import seedu.address.model.tag.Tag;

/**
 * Represents a Phone in the SML.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Phone implements Identifiable<Phone> {

    // Identity fields
    private final IdentityNumber identityNumber;
    private final SerialNumber serialNumber;

    // Data fields
    private final PhoneName phoneName;
    private final Brand brand;
    private final Capacity capacity;
    private final Colour colour;
    private final Cost cost;
    private final Set<Tag> tags = new HashSet<>();

    public Phone(IdentityNumber identityNumber, SerialNumber serialNumber, PhoneName phoneName, Brand brand,
                 Capacity capacity, Colour colour, Cost cost, Set<Tag> tags) {
        requireAllNonNull(identityNumber, serialNumber, phoneName, brand, capacity, colour, cost, tags);
        this.identityNumber = identityNumber;
        this.serialNumber = serialNumber;
        this.phoneName = phoneName;
        this.brand = brand;
        this.capacity = capacity;
        this.colour = colour;
        this.cost = cost;
        this.tags.addAll(tags);
    }

    public IdentityNumber getIdentityNumber() {
        return identityNumber;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
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
     * This defines a weaker notion of equality between two phones.
     */
    public boolean isSamePhone(Phone otherPhone) {
        if (otherPhone == this) {
            return true;
        }

        return otherPhone != null
                && (otherPhone.getIdentityNumber().equals(getIdentityNumber())
                || otherPhone.getSerialNumber().equals(getSerialNumber()));
    }

    /**
     * Returns true if both phones have the same identity and data fields.
     * This defines a stronger notion of equality between two phones.
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
        return otherPhone.getIdentityNumber().equals(getIdentityNumber())
                && otherPhone.getSerialNumber().equals(getSerialNumber())
                && otherPhone.getPhoneName().equals(getPhoneName())
                && otherPhone.getBrand().equals((getBrand()))
                && otherPhone.getCapacity().equals((getCapacity()))
                && otherPhone.getColour().equals((getColour()))
                && otherPhone.getCost().equals(getCost())
                && otherPhone.getTags().equals((getTags()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(identityNumber, serialNumber, phoneName, brand, capacity, colour, cost, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" IMEI: ")
                .append(getIdentityNumber())
                .append(" SN: ")
                .append(getSerialNumber())
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

    @Override
    public boolean isSameAs(Phone other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getIdentityNumber().equals(getIdentityNumber())
                && other.getSerialNumber().equals(getSerialNumber());
    }

}
