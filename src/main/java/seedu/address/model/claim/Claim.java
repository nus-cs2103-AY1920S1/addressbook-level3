package seedu.address.model.claim;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.commonVariables.Name;
import seedu.address.model.commonVariables.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represent a Claim in the Financial Planner
 */
public class Claim {

    // Identity fields
    private final Description description;
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Amount amount;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Claim(Description description, Amount amount, Name name, Phone phone, Set<Tag> tags) {
        requireAllNonNull(description, amount, name, phone, tags);
        this.description = description;
        this.amount = amount;
        this.name = name;
        this.phone = phone;
        this.tags.addAll(tags);
    }

    public Description getDescription() {
        return description;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Amount getAmount() {
        return amount;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * This defines a weaker notion of equality between two claims.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both claims of the same description has at least one other identify field that is the asme.
     */
    public boolean isSameClaim(Claim otherClaim) {
        if (otherClaim == this) {
            return true;
        }

        return otherClaim != null
                && otherClaim.getName().equals(getName())
                && (otherClaim.getDescription().equals(getDescription()) || otherClaim.getPhone().equals(getPhone()));
    }

    /**
     * Returns true if both claims have same identity and data fields.
     * This defines a stronger notion of equality between two claims.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Claim)) {
            return false;
        }

        Claim otherClaim = (Claim) obj;
        return otherClaim.getDescription().equals(getDescription())
                && otherClaim.getName().equals(getName())
                && otherClaim.getPhone().equals(getPhone())
                && otherClaim.getAmount().equals(getAmount())
                && otherClaim.getTags().equals(getTags());
    }
}
