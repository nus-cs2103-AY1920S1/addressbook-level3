package seedu.system.model.attempt;

import java.io.IOException;

import seedu.system.model.attempt.exceptions.AttemptHasBeenAttemptedException;
import seedu.system.model.exercise.Exercise;
import seedu.system.model.exercise.Lift;

/**
 * Represents a {@link seedu.system.model.person.Person}'s attempt in an {@link Exercise}.
 * Guarantees: immutable;
 */
public class Attempt {

    public static final String MESSAGE_CONSTRAINTS =
        "Attempt should follow the following format DD/MM/YYYY.";
    private static final String TRUE_IN_STRING_FORM = "true";

    private final Lift lift;
    private final int weight;

    private boolean hasAttempted;
    private boolean isSuccessful;

    public Attempt(Lift lift, int weight) {
        this.lift = lift;
        this.weight = weight;
        this.hasAttempted = false;
        this.isSuccessful = false;
    }

    public Attempt(Lift lift, int weight, boolean hasAttempted, boolean isSuccessful) {
        this.lift = lift;
        this.weight = weight;
        this.hasAttempted = hasAttempted;
        this.isSuccessful = isSuccessful;
    }

    /**
     * This method records the success of an athlete's attempt after his lift.
     *
     * @param isSuccessful true if the athlete succeeds his lift, false otherwise
     */
    public void setSuccess(boolean isSuccessful) throws AttemptHasBeenAttemptedException {
        if (hasAttempted) {
            throw new AttemptHasBeenAttemptedException();
        }

        this.isSuccessful = isSuccessful;
        this.hasAttempted = true;
    }

    public int getWeightAttempted() {
        return weight;
    }

    public boolean getHasAttempted() {
        return hasAttempted;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }

    public Lift getLift() {
        return lift;
    }

    /**
     * Converts a given {@code Attempt} into string format for JSON storage.
     */
    public static String getStringStorageFormOfAttempt(Attempt attempt) {
        String storedString = "";
        storedString += attempt.getLift();
        storedString += " " + attempt.getHasAttempted();
        storedString += " " + attempt.getIsSuccessful();
        storedString += " " + attempt.getWeightAttempted();
        return storedString;
    }

    /**
     * Converts a given {@code String} into an Attempt.
     */
    public static Attempt parseStringToAttempt(String string) throws IOException {
        String[] splitString = string.split(" ");
        if (splitString.length < 4) {
            throw new IOException();
        }

        String liftName = splitString[0];
        Lift lift = Lift.getLiftCorrespondingToName(liftName);
        String stringVersonOfHasAttempted = splitString[1];
        boolean hasAttempted = stringVersonOfHasAttempted.equals(TRUE_IN_STRING_FORM);
        String stringVersonOfIsSuccessful = splitString[2];
        boolean isSuccessful = stringVersonOfIsSuccessful.equals(TRUE_IN_STRING_FORM);
        String stringVersonOfWeight = splitString[3];
        int weightAttempted = Integer.parseInt(stringVersonOfWeight);
        return new Attempt(lift, weightAttempted, hasAttempted, isSuccessful);
    }
}
