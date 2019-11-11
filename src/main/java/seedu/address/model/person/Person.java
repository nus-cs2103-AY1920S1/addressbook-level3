package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.binitem.Binnable;
import seedu.address.model.policy.Policy;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Binnable {

    // Identity fields
    private final Name name;
    private final Nric nric;
    private final Phone phone;
    private final Email email;
    private final DateOfBirth dateOfBirth;
    private final Gender gender;

    // Data fields
    private final Address address;
    private final Set<Policy> policies = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name,
                  Nric nric,
                  Phone phone,
                  Email email,
                  Address address,
                  DateOfBirth dateOfBirth,
                  Gender gender,
                  Set<Policy> policies,
                  Set<Tag> tags) {
        requireAllNonNull(name, nric, phone, email, address, dateOfBirth, policies, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.policies.addAll(policies);
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    /**
     * Returns an immutable policy set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Policy> getPolicies() {
        return Collections.unmodifiableSet(policies);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name and nric.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
            && otherPerson.getNric().equals(getNric());
    }

    /**
     * Returns true if the person has the policy in the policies list.
     * Used as a check during the Assign Policy Command.
     */
    public boolean hasPolicy(Policy policy) {
        return this.policies.contains((Policy) policy);
    }

    /**
     * Returns true if both persons of the same editable fields.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean hasEqualEditableFields(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
            && otherPerson.getName().equals(getName())
            && otherPerson.getNric().equals(getNric())
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getAddress().equals(getAddress())
            && otherPerson.getEmail().equals(getEmail())
            && otherPerson.getDateOfBirth().equals(getDateOfBirth())
            && otherPerson.getGender().equals(getGender());
    }

    /**
     * Returns a {@code Set<Policy>} of policies that the person owns that he is eligible for.
     */
    public Set<Policy> getEligiblePolicies() {
        HashSet<Policy> eligiblePolicies = new HashSet<>();
        for (Policy policy : policies) {
            if (policy.isEligible(this)) {
                eligiblePolicies.add(policy);
            }
        }
        return eligiblePolicies;
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

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
            && otherPerson.getNric().equals(getNric())
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getEmail().equals(getEmail())
            && otherPerson.getAddress().equals(getAddress())
            && otherPerson.getDateOfBirth().equals(getDateOfBirth())
            && otherPerson.getGender().equals(getGender())
            && otherPerson.getPolicies().equals(getPolicies())
            && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, phone, email, address, dateOfBirth, gender, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append("\n")
            .append("NRIC: ")
            .append(getNric())
            .append("; Phone: ")
            .append(getPhone())
            .append("; Email: ")
            .append(getEmail())
            .append("; Address: ")
            .append(getAddress())
            .append("; Date of Birth: ")
            .append(getDateOfBirth())
            .append("; Gender: ")
            .append(getGender());
        if (getPolicies().size() != 0) {
            builder.append("\n");
            builder.append("Policies: ");
            getPolicies().forEach(policy -> builder.append("[" + policy.getName().policyName + "]"));
        }
        if (getTags().size() != 0) {
            builder.append("\n");
            builder.append("Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }

}
