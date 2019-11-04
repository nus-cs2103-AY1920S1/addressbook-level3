package seedu.address.model.events;

import seedu.address.model.ReferenceId;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;

/**
 * new Appoint with timing and status and personId
 * no need to relate to personName, instead of playing with personId
 */
public class Appointment extends Event {
    /**
     * Every field must be present and not null.
     *
     * @param personId
     * @param timing
     * @param status
     */
    public Appointment(ReferenceId personId, Timing timing, Status status) {
        super(personId, timing, status);
    }
}
