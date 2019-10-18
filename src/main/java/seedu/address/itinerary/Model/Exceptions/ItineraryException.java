package seedu.address.itinerary.model.Exceptions;

public class ItineraryException extends Exception {
    /**
     * Constructor for the ItineraryException class. It contains the error message
     * which specifies the error that occurred.
     *
     * @param message Takes in the error message which indicates the error.
     */
    public ItineraryException(String message) {
        super(message);
    }
}
