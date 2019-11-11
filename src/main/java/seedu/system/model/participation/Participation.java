package seedu.system.model.participation;

import java.util.ArrayList;
import java.util.List;

import seedu.system.logic.commands.RankMethod;
import seedu.system.model.UniqueElement;
import seedu.system.model.attempt.Attempt;
import seedu.system.model.competition.Competition;
import seedu.system.model.exercise.Exercise;
import seedu.system.model.exercise.Lift;
import seedu.system.model.person.Name;
import seedu.system.model.person.Person;

/**
 * Represents a {@link Person}'s participation in a {@link Competition}.
 * Guarantees: immutable; person-competition pair is unique.
 */
public class Participation extends UniqueElement {
    private final Person person;
    private final Competition competition;

    private List<Attempt> attempts;
    private boolean areAttemptsSubmitted;

    public Participation(Person person, Competition competition) {
        this.person = person;
        this.competition = competition;
        this.attempts = new ArrayList<>(9);
        this.areAttemptsSubmitted = false;
    }

    public Participation(Person person, Competition competition, List<Attempt> attempts) {
        this.person = person;
        this.competition = competition;
        this.attempts = attempts;
        this.areAttemptsSubmitted = true;
    }

    /**
     * This method adds all the weight to be attempted for this participation.
     *
     * @param weightOfAttemptsList a list of the weight to be attempted for eaCh lift and attempt
     */
    public void addAttempts(List<Integer> weightOfAttemptsList) {
        List<Exercise> exerciseList = competition.getExerciseList();
        int index = 0;
        for (Exercise exercise : exerciseList) {
            Lift lift = exercise.getLift();
            int noOfAttempts = exercise.getNoOfAttempts();
            for (int i = 0; i < noOfAttempts && index < 9; i++) {
                attempts.add(new Attempt(lift, weightOfAttemptsList.get(index)));
                index++;
            }
        }
        areAttemptsSubmitted = true;
    }

    /**
     * Updates the success of the attempt after the lift.
     *
     * @param index attempt index which relates to the lift and attempt
     * @param isSuccess a boolean indicating the success of the attempt
     */
    public void updateAttempt(int index, boolean isSuccess) {
        Attempt attempt = attempts.get(index - 1);
        assert attempt != null;
        attempt.setSuccess(isSuccess);
        attempts.set(index - 1, attempt);
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

    public Name getName() {
        return person.getName();
    }

    public boolean getAreAttemptsSubmitted() {
        return areAttemptsSubmitted;
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
     * @return the score of a participation based on the rank method given
     */
    public int getScore(RankMethod rankMethod) {
        switch (rankMethod) {
        case SQUAT:
            return getLiftScore(Lift.SQUAT);
        case BENCH:
            return getLiftScore(Lift.BENCH);
        case DEADLIFT:
            return getLiftScore(Lift.DEADLIFT);
        default:
            return getTotalScore();
        }
    }

    /**
     * Gets the highest score of each of the three lifts in this format: Squat/Bench/Deadlift.
     * @return a string representation of the three lift score
     */
    public String getThreeLiftScore() {
        StringBuilder topAttemptsString = new StringBuilder();
        for (Exercise exercise : competition.getExerciseList()) {
            Lift lift = exercise.getLift();
            topAttemptsString.append("/").append(getLiftScore(lift));
        }
        String outputAttempts = topAttemptsString.toString();
        return outputAttempts.substring(1);
    }

    /**
     * Returns the string representation of a participation object.
     */
    public String toString() {
        return getName() + " in " + competition.toString();
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
