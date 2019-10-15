package seedu.address.model.competition;

import java.util.Date;
import java.util.Objects;

import seedu.address.model.UniqueElement;
import seedu.address.model.UniqueElementList;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Name;

/**
 * Represents a Competition in the app.
 */
public class Competition extends UniqueElement {
    private final Name name; // to be replaced
    private final Date startDate;
    private final Date endDate;
    private final UniqueElementList<Exercise> exerciseList;
    private final UniqueElementList<Participation> participationList;

    public Competition(Name name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exerciseList = new UniqueElementList<>();
        this.participationList = new UniqueElementList<>();
    }

    public Name getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    /**
     * Returns true if both exercises have the same identity and data fields.
     */
    public boolean isSameElement(UniqueElement otherElement) {

        if (!(otherElement instanceof Competition)) {
            return false;
        }

        return this.equals((Competition) otherElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Competition)) {
            return false;
        }

        Competition otherPerson = (Competition) other;
        return otherPerson.getName().equals(getName());
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
