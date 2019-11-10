package seedu.address.model.vehicle;

import static seedu.address.model.vehicle.Availability.VEHICLE_AVAILABLE_TAG;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 * Only used for manual vehicle assignment, where model shows only the available
 * vehicles in the district.
 */
public class AvailableInDistrictPredicate implements Predicate<Vehicle> {
    private final District district;

    /**
     * Add search keywords as list (of strings) to match
     * Accounts for possibly multiple search parameters.
     * @param district
     */
    public AvailableInDistrictPredicate(District district) {
        this.district = district;
    }

    /**
     * Looks for matching vehicles by comparing District directly
     * @param vehicle
     * @return
     */
    @Override
    public boolean test(Vehicle vehicle) {
        return vehicle.getDistrict().getDistrictNum() == this.district.getDistrictNum()
                && vehicle.getAvailability().getAvailabilityTag().equals(VEHICLE_AVAILABLE_TAG);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AvailableInDistrictPredicate // instanceof handles nulls
                && district.equals(((AvailableInDistrictPredicate) other).district)); // state check
    }

    public String getPredicate() {
        return "Available in district " + district;
    }
}
