package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class Policy {

    private static final ArrayList<Policy> policies = new ArrayList<>();

    private final PolicyName name;
    private final Description description;
    private final Coverage coverage;
    private final Price price;
    private final StartAge startAge;
    private final EndAge endAge;

    private final Set<Tag> criteria = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    public static final String MESSAGE_CONSTRAINTS =
            "This policy does not exist.";

    public Policy(PolicyName name, Description description, Coverage coverage, Price price, StartAge startAge,
                  EndAge endAge, Set<Tag> criteria, Set<Tag> tags) {
        requireAllNonNull(name, description, coverage, price, startAge, endAge, criteria, tags);
        this.name = name;
        this.description = description;
        this.coverage = coverage;
        this.price = price;
        this.startAge = startAge;
        this.endAge = endAge;
        this.criteria.addAll(criteria);
        this.tags.addAll(tags);
        policies.add(this);
    }

    public PolicyName getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public Price getPrice() {
        return price;
    }

    public StartAge getStartAge() {
        return startAge;
    }

    public EndAge getEndAge() {
        return endAge;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePolicy(Policy otherPolicy) {
        if (otherPolicy == this) {
            return true;
        }

        return otherPolicy != null
                && otherPolicy.getName().equals(getName());
    }

    public static boolean isAvailable(String policyName) {
        for (int i = 0; i < policies.size(); i++) {
            if (policies.get(i).getName().equals(policyName)) {
                return true;
            }
        }
        return false;
    }

    public static Policy getPolicy(String policyName) {
        for (int i = 0; i < policies.size(); i++) {
            Policy policy = policies.get(i);
            if (policy.getName().equals(policyName)) {
                return policy;
            }
        }
        return null;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Policy)) {
            return false;
        }

        Policy otherPolicy = (Policy) other;
        return otherPolicy.getName().equals(getName())
                && otherPolicy.getDescription().equals(getDescription())
                && otherPolicy.getCoverage().equals(getCoverage())
                && otherPolicy.getPrice().equals(getPrice())
                && otherPolicy.getStartAge().equals(getStartAge())
                && otherPolicy.getEndAge().equals(getEndAge())
                && otherPolicy.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, coverage, price, startAge, endAge, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Coverage: ")
                .append(getCoverage())
                .append(" Price: ")
                .append(getPrice())
                .append(" Start Age: ")
                .append(getStartAge())
                .append(" End Age: ")
                .append(getEndAge())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
