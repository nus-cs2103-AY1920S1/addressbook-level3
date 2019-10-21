package seedu.address.model.participation;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.UniqueElement;
import seedu.address.model.attempt.Attempt;
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
    private final List<Attempt> attempts;


    public Participation(Person person, Competition competition) {
        this.person = person;
        this.competition = competition;
        this.attempts = createNewListOfAttempts(competition.getExerciseList());
    }

    public Participation(Person person, Competition competition, List<Attempt> attempts) {
        this.person = person;
        this.competition = competition;
        this.attempts = attempts;
    }

    /**
     *
     * @param exerciseList a list of exercises for the competition
     * @return list of attempts to track the athlete progress throughout the competition
     */
    private List<Attempt> createNewListOfAttempts(List<Exercise> exerciseList) {
        List<Attempt> attempts = new ArrayList<>();
        int initialWeight = 0;
        for (Exercise exercise : exerciseList) {
            Lift lift = exercise.getLift();
            int noOfAttempts = exercise.getNoOfAttempts();
            for (int i = 0; i < noOfAttempts; i++) {
                attempts.add(new Attempt(lift, initialWeight));
            }
        }
        return attempts;
    }

    public Person getPerson() {
        return person;
    }

    public Competition getCompetition() {
        return competition;
    }

    public List<Attempt> getAttempts() {
        return attempts;
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
     * @return the total score of the person's participation at a specified competition
     */
    public int getTotalScore() {
        int score = 0;
        for (Exercise exercise : competition.getExerciseList()) {
            score += getLiftScore(exercise.getLift());
        }
        return score;
    }

    /**
     * @return the total score of the person's participation at a specified competition for a specified lift
     */
    public int getLiftScore(Lift lift) {
        int score = 0;
        for (Attempt attempt : attempts) {
            if (attempt.getLift() == lift && attempt.getIsSuccessful()) {
                score = Math.max(score, attempt.getWeightAttempted());
            }
        }
        return score;
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
