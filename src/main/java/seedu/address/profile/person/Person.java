package seedu.address.profile.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.profile.medical.MedicalHistory;

/**
 * Represents a Person in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;

    // Data fields
    private final DoB dateOfBirth;
    private final Gender gender;
    private final BloodType bloodGroup;
    private final Weight weight;
    private final Height height;
    private final Set<MedicalHistory> medicalHistories = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, DoB dateOfBirth, Gender gender, BloodType bloodGroup,
                  Weight weight, Height height, Set<MedicalHistory> medicalHistories) {
        requireAllNonNull(name, dateOfBirth, gender, bloodGroup, weight, height, medicalHistories);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.weight = weight;
        this.height = height;
        this.medicalHistories.addAll(medicalHistories);
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public DoB getDateOfBirth() {
        return dateOfBirth;
    }

    public BloodType getBloodType() {
        return bloodGroup;
    }

    public Weight getWeight() {
        return weight;
    }

    public Height getHeight() {
        return height;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<MedicalHistory> getMedicalHistories() {
        return Collections.unmodifiableSet(medicalHistories);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
                && otherPerson.getMedicalHistories().equals(getMedicalHistories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, medicalHistories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Tags: ");
        getMedicalHistories().forEach(builder::append);
        return builder.toString();
    }

}
