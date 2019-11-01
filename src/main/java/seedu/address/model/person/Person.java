package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.classid.ClassId;

/**
 * Represents a Student in the class of the user.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person {

    private final Name name;
    private final Picture picture;
    private ClassId classId;
    private Attendance attendance;
    private Result result;
    private Participation participation;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, ClassId classId) {
        requireAllNonNull(name, classId);
        this.name = name;
        this.picture = new Picture("null");
        this.classId = classId;
        this.attendance = new Attendance("0");
        this.result = new Result("0");
        this.participation = new Participation("0");
    }

    public Person(Name name, Picture picture, ClassId classId, Attendance attendance,
                  Result result, Participation participation) {
        requireAllNonNull(name, picture);
        this.name = name;
        this.picture = picture;
        this.classId = classId;
        this.attendance = attendance;
        this.result = result;
        this.participation = participation;
    }

    public Name getName() {
        return name;
    }

    public ClassId getClassId() {
        return classId;
    }

    public Picture getPicture() {
        return picture;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public Result getResult() {
        return result;
    }

    public Participation getParticipation() {
        return participation;
    }

    public void setClassId(String value) {
        classId = new ClassId(value);
    }

    public void setAttendance(int value) {
        attendance = new Attendance(Integer.toString(value));
    }

    public void setResult(String value) {
        result = new Result(value);
    }

    public void setParticipation(String value) {
        participation = new Participation(value);
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
                && otherPerson.getName().equals(getName())
                && (otherPerson.getClassId().equals(getClassId()) || otherPerson.getPicture().equals(getPicture()));
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
                && otherPerson.getClassId().equals(getClassId())
                && otherPerson.getPicture().equals(getPicture())
                && otherPerson.getAttendance().equals(getAttendance())
                && otherPerson.getParticipation().equals(getParticipation())
                && otherPerson.getResult().equals(getResult());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, picture, classId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Class: ")
                .append(getClassId())
                .append(" Attendance: ")
                .append(getAttendance())
                .append(" Result: ")
                .append(getResult())
                .append(" Class Participation: ")
                .append(getParticipation());
        return builder.toString();
    }

}
