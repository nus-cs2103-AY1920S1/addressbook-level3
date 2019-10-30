package seedu.address.model.vehicle.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_NO_VEHICLES_FOUND;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException() {
        super(MESSAGE_NO_VEHICLES_FOUND);
    }
}
