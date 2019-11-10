package seedu.planner.model.day.exceptions;

//@@author KxxMxxx
/**
 * Signals that the operation exceeds the last day of the itinerary.
 */
public class EndOfTimeException extends RuntimeException {
    @Override
    public String toString() {
        return "Activity will end after the end of the itinerary.";
    }
}
