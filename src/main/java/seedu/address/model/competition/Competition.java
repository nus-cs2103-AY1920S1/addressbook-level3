package seedu.address.model.competition;

import java.util.Date;
import java.util.Objects;

import seedu.address.model.UniqueElementList;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Name;

/**
 * Represents a Competition in the app.
 */
public class Competition {
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

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, exerciseList, participationList);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); // stub
    }

    @Override
    public String toString() {
        return super.toString(); // stub
    }
}
