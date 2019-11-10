package seedu.address.model.vehicle;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.vehicle.Availability.VEHICLE_AVAILABLE_TAG;

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
        this.availability = availability;
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

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public boolean isAvailable() {
        return this.getAvailability().getAvailabilityTag().equals(VEHICLE_AVAILABLE_TAG);
    }

    /**
     * Returns true iff both Vehicles have the same vehicle number.
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
        return otherVehicle.getVehicleNumber().equals(getVehicleNumber())
                && otherVehicle.getVehicleType().equals(getVehicleType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(vehicleType, vehicleNumber, district, availability);
    }

    /**
     * Description of vehicle specifically used for UI.
     * @return description of vehicle to display on UI.
     */
    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getVehicleType())
                .append(" ")
                .append(getVehicleNumber());
        return sb.toString();
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

