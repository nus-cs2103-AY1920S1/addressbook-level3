package seedu.address.logic.commands.csvcommand.csvutil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.csvcommand.csvutil.ErrorTracker.Error;

public class ErrorTrackerTest {

    @Test
    public void toString_convertErrorToString_correctStringReturned() {
        Error error = new Error(1, "What's wrong?", "Alfred is sick");
        String expected = "\tLine 1: What's wrong? (Cause: Alfred is sick)";
        assertEquals(expected, error.toString());
    }

    @Test
    public void toString_convertErrorTrackerToString_correctStringReturned() {
        Error error1 = new Error(1, "What's wrong?", "Alfred is sick");
        Error error2 = new Error(2, "What's wrong?", "Batman is sick");
        Error error3 = new Error(3, "What's wrong?", "Joker is here");
        ErrorTracker errorTracker = new ErrorTracker(error1, error2, error3);
        String expected = String.join("\n", error1.toString(), error2.toString(), error3.toString());
        assertEquals(expected, errorTracker.toString());
    }

    @Test
    public void toString_differentOrderingOfErrors_sameStringReturned() {
        Error error1 = new Error(1, "What's wrong?", "Alfred is sick");
        Error error2 = new Error(2, "What's wrong?", "Batman is sick");
        Error error3 = new Error(3, "What's wrong?", "Joker is here");
        ErrorTracker errorTracker = new ErrorTracker(error2, error1, error3);
        String expected = String.join("\n", error1.toString(), error2.toString(), error3.toString());
        assertEquals(expected, errorTracker.toString());

        errorTracker = new ErrorTracker();
        errorTracker.add(error3);
        errorTracker.add(error2);
        errorTracker.add(error1);
        assertEquals(expected, errorTracker.toString());
    }

}
