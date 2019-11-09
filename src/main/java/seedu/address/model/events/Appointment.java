//@@author woon17
package seedu.address.model.events;

import seedu.address.model.ReferenceId;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;
import seedu.address.model.person.parameters.Name;

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
    public Appointment(ReferenceId personId, Name name, Timing timing, Status status) {
        super(personId, name, timing, status);
    }
}
