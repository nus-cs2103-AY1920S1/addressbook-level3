package seedu.address.model.vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class DistrictKeywordsPredicate implements Predicate<Vehicle> {
    private final List<String> keywords = new ArrayList<>();

    public DistrictKeywordsPredicate(District districtKeywords) {
        this.keywords.add(districtKeywords.toString());
    }

    @Override
    public boolean test(Vehicle vehicle) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(vehicle.getDistrict().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DistrictKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DistrictKeywordsPredicate) other).keywords)); // state check
    }

    public String getPredicate() {
        return keywords.toString();
    }
}
