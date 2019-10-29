package seedu.address.model.vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class DistrictKeywordsPredicate implements Predicate<Vehicle> {
    private List<District> keywords = new ArrayList<>();

    /**
     * Add search keywords as list (of strings) to match
     * Accounts for possibly multiple search parameters.
     * @param districtKeywords
     */
    public DistrictKeywordsPredicate(List<District> districtKeywords) {
        this.keywords.addAll(districtKeywords);
    }

    /**
     * Looks for matching vehicles by comparing District directly
     * @param vehicle
     * @return
     */
    @Override
    public boolean test(Vehicle vehicle) {
        return keywords.stream()
                .anyMatch(keyword -> vehicle.getDistrict().equals(keyword));
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
