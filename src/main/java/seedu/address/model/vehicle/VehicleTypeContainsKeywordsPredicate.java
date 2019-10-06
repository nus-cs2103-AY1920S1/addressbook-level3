package seedu.address.model.vehicle;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Vehicle}'s {@code VehicleType} matches any of the keywords given.
 */
public class VehicleTypeContainsKeywordsPredicate implements Predicate<Vehicle> {
    private final List<String> keywords;

    public VehicleTypeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Vehicle vehicle) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(vehicle.getVehicleType().vehicleType, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VehicleTypeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((VehicleTypeContainsKeywordsPredicate) other).keywords)); // state check
    }
}
