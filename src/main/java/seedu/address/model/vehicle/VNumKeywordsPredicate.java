package seedu.address.model.vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class VNumKeywordsPredicate implements Predicate<Vehicle> {
    private final List<String> keywords = new ArrayList<>();

    public VNumKeywordsPredicate(VehicleNumber vNumKeywords) {
        this.keywords.add(vNumKeywords.toString());
    }

    @Override
    public boolean test(Vehicle vehicle) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(vehicle.getVehicleNumber().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VNumKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((VNumKeywordsPredicate) other).keywords)); // state check
    }

    public String getPredicate() {
        return keywords.toString();
    }
}
