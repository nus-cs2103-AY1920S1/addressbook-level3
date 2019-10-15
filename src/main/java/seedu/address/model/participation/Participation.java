package seedu.address.model.participation;

import seedu.address.model.UniqueElement;
import seedu.address.model.competition.Competition;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.Lift;
import seedu.address.model.person.Person;

/**
 * Represents a {@link Person}'s participation in a {@link Competition}.
 * Guarantees: immutable; person-competition pair is unique.
 */
public class Participation extends UniqueElement {
    private final Person person;
    private final Competition competition;

    private Exercise squat = new Exercise(Lift.Squat);
    private Exercise bench = new Exercise(Lift.Bench);
    private Exercise deadlift = new Exercise(Lift.Deadlift);

    public Participation(Person person, Competition competition) {
        this.person = person;
        this.competition = competition;
    }

    public Person getPerson() {
        return person;
    }

    public Competition getCompetition() {
        return competition;
    }

    /**
     * Returns true if both exercises have the same identity and data fields.
     */
    public boolean isSameElement(UniqueElement otherElement) {

        if (!(otherElement instanceof Participation)) {
            return false;
        }

        return this.equals((Participation) otherElement);
    }

    /**
     * Returns the Exercise object for this Participation object's lifts.
     * @param typeOfExercise the Lift for the Exercise object
     * @return the Exercise object with respect to the typeOfExercise
     */
    public Exercise getExercise(Lift typeOfExercise) {
        switch (typeOfExercise) {
        case Squat:
            return this.squat;

        case Bench:
            return this.bench;

        case Deadlift:
            return this.deadlift;

        default:
            return null;
        }
    }

    /**
     * Returns true if both participations have the same identity and data fields.
     * This defines a stronger notion of equality between two participations.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Participation)) {
            return false;
        }

        Participation otherParticipation = (Participation) other;
        return otherParticipation.getPerson().equals(getPerson())
                && otherParticipation.getCompetition().equals(getCompetition());
    }
}
