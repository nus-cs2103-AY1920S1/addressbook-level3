package seedu.address.testutil;

import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

/**
 * A utility class to help with building of Vehicle objects.
 */
public class VehicleBuilder {
    public static final String DEFAULT_DISTRICT = "21";
    public static final String DEFAULT_AVAILABILITY = "Available";
    public static final String DEFAULT_VTYPE = "Ambulance";
    public static final String DEFAULT_VNUM = "SBH3100F";

    private District district;
    private Availability availability;
    private VehicleNumber vNum;
    private VehicleType vType;

    public VehicleBuilder() {
        this.district = new District(Integer.parseInt(DEFAULT_DISTRICT));
        this.availability = new Availability(DEFAULT_AVAILABILITY);
        this.vNum = new VehicleNumber(DEFAULT_VNUM);
        this.vType = new VehicleType(DEFAULT_VTYPE);
    }

    /**
     * Initialise VehicleBuilder with fields from {@code toCopy}
     */
    public VehicleBuilder(VehicleBuilder toCopy) {
        this.district = toCopy.district;
        this.availability = toCopy.availability;
        this.vNum = toCopy.vNum;
        this.vType = toCopy.vType;
    }

    /**
     * Initialise VehicleBuilder with details from {@code Vehicle vehicle}
     */
    public VehicleBuilder(Vehicle vehicle) {
        this.district = vehicle.getDistrict();
        this.availability = vehicle.getAvailability();
        this.vNum = vehicle.getVehicleNumber();
        this.vType = vehicle.getVehicleType();
    }

    /**
     * Sets the {@code district} of the vehicle.
     */
    public VehicleBuilder withDistrict(String district) {
        this.district = new District(Integer.parseInt(district));
        return this;
    }

    /**
     * Sets the {@code vNum} of the vehicle.
     */
    public VehicleBuilder withVNum(String vNum) {
        this.vNum = new VehicleNumber(vNum);
        return this;
    }

    /**
     * Sets the {@code vType} of the vehicle
     */
    public VehicleBuilder withVType(String vType) {
        this.vType = new VehicleType(vType);
        return this;
    }

    /**
     * Sets the {@code availability} of the vehicle
     */
    public VehicleBuilder withAvail(String avail) {
        this.availability = new Availability(avail);
        return this;
    }

    public Vehicle build() {
        return new Vehicle(this.vType, this.vNum, this.district, this.availability);
    }
}
