package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.IncidentManager;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalVehicles {

    public static final Vehicle V1 = new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("SGS2121G"),
                new District(1), new Availability("AVAILABLE"));

    public static final Vehicle V2 = new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("BBA2222F"),
                new District(6), new Availability("BUSY"));

    public static final Vehicle V3 = new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("FKH1221P"),
                new District(20), new Availability("AVAILABLE"));

    public static final Vehicle V4 = new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("OLI4445C"),
                new District(2), new Availability("BUSY"));

    public static final Vehicle V5 = new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("GER4389F"),
            new District(4), new Availability("AVAILABLE"));

    public static final Vehicle V6 = new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("BBA2222F"),
            new District(1), new Availability("BUSY"));

    public static final Vehicle V7 = new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("FKH1291P"),
            new District(20), new Availability("AVAILABLE"));

    private TypicalVehicles() {} // prevents instantiation

    /**
     * Returns an {@code IncidentManager} with all the typical vehicles.
     */
    public static IncidentManager getTypicalIncidentManager() {
        IncidentManager ab = new IncidentManager();
        for (Vehicle vehicle : getTypicalVehicles()) {
            ab.addVehicle(vehicle);
        }
        return ab;
    }

    public static List<Vehicle> getTypicalVehicles() {
        return new ArrayList<>(Arrays.asList(V1, V2, V3, V4, V5));
    }
}
