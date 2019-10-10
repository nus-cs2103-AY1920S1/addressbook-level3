package seedu.address.model.person.exceptions;

/**
 * Signals that the incoming duration will result in a scheduling conflict (i.e. outside working hours, or conflict
 * with existing schedule)
 *
 */
public class SchedulingException extends RuntimeException {
    public SchedulingException(String e) {
        super(e);
    }
}
