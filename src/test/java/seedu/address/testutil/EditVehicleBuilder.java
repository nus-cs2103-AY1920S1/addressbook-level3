package seedu.address.testutil;

import seedu.address.logic.commands.EditVehicleCommand.EditVehicle;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.VehicleType;

/**
 * A utility class for building EditVehicle Objects
 */
public class EditVehicleBuilder {
    private EditVehicle editor;

    public EditVehicleBuilder() {
        editor = new EditVehicle();
    }

    public EditVehicleBuilder(Vehicle vehicle) {
        editor = new EditVehicle();
        editor.setVehicleAvailability(vehicle.getAvailability());
        editor.setVehicleType(vehicle.getVehicleType());
        editor.setVehicleDistrict(vehicle.getDistrict());
    }
    /**
     * Sets the {@code District} of the EditVehicle object being built to {@code district} .
     */
    public EditVehicleBuilder withDistrict(String district) {
        editor.setVehicleDistrict(new District(Integer.parseInt(district)));
        return this;
    }

    /**
     * Sets the {@code VehicleType} of the EditVehicle object to {@code vType}
     */
    public EditVehicleBuilder withVType(String vType) {
        editor.setVehicleType(new VehicleType(vType));
        return this;
    }

    /**
     * Sets the {@code Availability} of the EditVehicle object to {@code avail}
     */
    public EditVehicleBuilder withAvail(String avail) {
        editor.setVehicleAvailability(new Availability(avail));
        return this;
    }

    public EditVehicle build() {
        return editor;
    }
}
