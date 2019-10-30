package seedu.system.model.competition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.system.model.UniqueElement;
import seedu.system.model.exercise.Exercise;
import seedu.system.model.exercise.Lift;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Name;

/**
 * Represents a Competition in the app.
 */
public class Competition extends UniqueElement {

    private static final Exercise squat = new Exercise(Lift.SQUAT);
    private static final Exercise bench = new Exercise(Lift.BENCH);
    private static final Exercise deadlift = new Exercise(Lift.DEADLIFT);

    private Name name;
    private CustomDate startDate;
    private CustomDate endDate;
    private final List<Exercise> exerciseList;

    public Competition(Name name, CustomDate startDate, CustomDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exerciseList = new ArrayList<>();
        this.exerciseList.add(squat);
        this.exerciseList.add(bench);
        this.exerciseList.add(deadlift);
    }

    public Name getName() {
        return name;
    }

    public CustomDate getStartDate() {
        return startDate;
    }

    public CustomDate getEndDate() {
        return endDate;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
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

        Competition otherCompetition = (Competition) other;
        return otherCompetition.getName().equals(getName());
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
