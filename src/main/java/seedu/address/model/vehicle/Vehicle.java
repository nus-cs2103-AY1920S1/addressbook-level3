package seedu.address.model.vehicle;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Vehicle in the IMS.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Vehicle {

    // Identity fields
    private final VehicleType vehicleType;
    private final VehicleNumber vehicleNumber;

    // Data fields
    private final District district; // where the vehicle is parked at
    private Availability availability;

    /**
     * Every field must be present and not null.
     */
    public Vehicle(VehicleType vehicleType, VehicleNumber vehicleNumber, District district, Availability availability) {
        requireAllNonNull(vehicleType, vehicleNumber, district);
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.district = district;
        this.availability = availability; // vehicles start off available
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public VehicleNumber getVehicleNumber() {
        return vehicleNumber;
    }

    public District getDistrict() {
        return district;
    }

    public Availability getAvailability() {
        return availability;
    }

    /**
     * Returns true if both Vehicles of the same VehicleType have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two Vehicles.
     */
    public boolean isSameVehicle(Vehicle otherVehicle) {
        if (otherVehicle == this) {
            return true;
        }

        return otherVehicle != null
                && otherVehicle.getVehicleType().equals(getVehicleType())
                && otherVehicle.getVehicleNumber().equals(getVehicleNumber());
    }

    /**
     * Returns true if both Vehicles have the same identity and data fields.
     * This defines a stronger notion of equality between two Vehicles.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Vehicle)) {
            return false;
        }

        Vehicle otherVehicle = (Vehicle) other;
        return otherVehicle.getVehicleType().equals(getVehicleType())
                && otherVehicle.getVehicleNumber().equals(getVehicleNumber());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(vehicleType, vehicleNumber, district, availability);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getVehicleType())
                .append(" Vehicle Number: ")
                .append(getVehicleNumber())
                .append(" District: ")
                .append(getDistrict())
                .append(" Availability: ")
                .append(getAvailability());
        return builder.toString();
    }

}

