package seedu.address.model.vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class VTypeKeywordsPredicate implements Predicate<Vehicle> {
    private final List<String> keywords = new ArrayList<>();

    public VTypeKeywordsPredicate(VehicleType vTypeKeywords) {
        this.keywords.add(vTypeKeywords.toString()); // only 1 keyword
    }

    @Override
    public boolean test(Vehicle vehicle) {
        String vType = keywords.get(0);
        return vehicle.getVehicleType().toString().equals(vType);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VTypeKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((VTypeKeywordsPredicate) other).keywords)); // state check
    }

    public String getPredicate() {
        return keywords.toString();
    }
}
