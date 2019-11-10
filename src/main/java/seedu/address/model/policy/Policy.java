package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.binitem.Binnable;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a Policy in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Policy implements Binnable {

    public static final String MESSAGE_CONSTRAINTS =
            "This policy is not available.";

    public static final String DATA_TYPE = "POLICY";

    private final PolicyName name;
    private final Description description;
    private final Coverage coverage;
    private final Price price;
    private final StartAge startAge;
    private final EndAge endAge;

    private final Set<Tag> criteria = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
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
     * Returns an immutable criteria set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getCriteria() {
        return Collections.unmodifiableSet(criteria);
    }

    /**
     * Returns true if both policies of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two policies.
     */
    public boolean isSamePolicy(Policy otherPolicy) {
        if (otherPolicy == this) {
            return true;
        }

        return otherPolicy != null
                && otherPolicy.getName().equals(getName());
    }

    /**
     * Returns true if both policies of the same editable fields.
     * This defines a weaker notion of equality between two policies.
     */
    public boolean hasEqualEditableFields(Policy otherPolicy) {
        if (otherPolicy == this) {
            return true;
        }

        return otherPolicy != null
                && otherPolicy.getName().equals(getName())
                && otherPolicy.getDescription().equals(getDescription())
                && otherPolicy.getCoverage().equals(getCoverage())
                && otherPolicy.getPrice().equals(getPrice())
                && otherPolicy.getStartAge().equals(getStartAge())
                && otherPolicy.getEndAge().equals(getEndAge());
    }


    /**
     * Returns true if both policies have the same identity and data fields.
     * This defines a stronger notion of equality between two policies.
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
                && otherPolicy.getCriteria().equals(getCriteria())
                && otherPolicy.getTags().equals(getTags());
    }

    /**
     * Returns true if the specified {@code Person} is eligible for the policy.
     */
    public boolean isEligible(Person person) {
        LocalDate localDateOfBirth = person.getDateOfBirth().toLocalDate();
        int birthYear = localDateOfBirth.getYear();
        int birthDayOfYear = localDateOfBirth.getDayOfYear();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        int age = currentYear - birthYear;

        if (birthDayOfYear < currentDayOfYear) {
            age -= 1;
        }

        return person.getTags().containsAll(criteria)
                && isEligibleAge(age);
    }

    /**
     * Returns true if the specified {@code int} is within the age range of the policy.
     */
    public boolean isEligibleAge(int age) {
            return age > Integer.parseInt(this.startAge.age) && age < Integer.parseInt(this.endAge.age);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nDescription: ")
                .append(getDescription())
                .append("; Coverage: ")
                .append(getCoverage().toReadableString())
                .append("; Price: ")
                .append(getPrice());
        if (!startAge.age.equals(StartAge.AGE_ZERO)) {
            builder.append("; Start Age: ")
                    .append(getStartAge());
        }
        if (!endAge.age.equals(EndAge.AGE_INFINITY)) {
            builder.append("; End Age: ")
                    .append(getEndAge());
        }
        if (getCriteria().size() != 0) {
            builder.append("\nCriteria: ");
            getCriteria().forEach(builder::append);
        }
        if (getTags().size() != 0) {
            builder.append("\nTags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
