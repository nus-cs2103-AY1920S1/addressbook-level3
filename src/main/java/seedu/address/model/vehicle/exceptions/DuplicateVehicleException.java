package seedu.address.model.vehicle.exceptions;

/**
 * Signals that the operation will result in duplicate Vehicles
 * (Vehicles are considered duplicates if they have the same identity).
 */
public class DuplicateVehicleException extends RuntimeException {
    public DuplicateVehicleException() {
        super("Operation would result in duplicate vehicles");
    }
}
