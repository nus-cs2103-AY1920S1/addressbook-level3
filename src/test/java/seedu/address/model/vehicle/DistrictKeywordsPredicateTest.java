package seedu.address.model.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalVehicles;

public class DistrictKeywordsPredicateTest {

    private List<District> districts1 = new ArrayList<>();
    private List<District> districts2 = new ArrayList<>();
    private List<District> districts3 = new ArrayList<>();

    private List<Vehicle> vehicles = TypicalVehicles.getTypicalVehicles();

    @Test
    public void equals() {
        districts3.add(vehicles.get(0).getDistrict());

        for (int i = 0; i < vehicles.size(); i++) {
            districts1.add(vehicles.get(i).getDistrict());
        }

        DistrictKeywordsPredicate p1 = new DistrictKeywordsPredicate(districts1);
        DistrictKeywordsPredicate p3 = new DistrictKeywordsPredicate(districts3);
        DistrictKeywordsPredicate p4 = new DistrictKeywordsPredicate(districts3);

        // same object -> returns true
        assertEquals(p1, p1);

        // same keyword -> returns true
        assertEquals(p3, p4);

        // diff keywords -> returns false
        assertFalse(p1.equals(p3));
    }

    @Test
    public void test_singleVehicleInSingleDistrict_returnsTrue() {
        districts3.add(vehicles.get(0).getDistrict());

        DistrictKeywordsPredicate p3 = new DistrictKeywordsPredicate(districts3);

        assertTrue(p3.test(vehicles.get(0)));
    }

    @Test
    public void test_singleVehicleInAnyOfTheMultipleDistricts_returnsTrue() {
        for (int i = 0; i < vehicles.size(); i++) {
            if (i % 2 == 0) {
                districts2.add(vehicles.get(i).getDistrict());
            }
            districts1.add(vehicles.get(i).getDistrict());
        }

        DistrictKeywordsPredicate p1 = new DistrictKeywordsPredicate(districts1);
        DistrictKeywordsPredicate p2 = new DistrictKeywordsPredicate(districts2);

        assertTrue(p1.test(vehicles.get(0)));
        assertTrue(p2.test(vehicles.get(0)));
    }

    @Test
    public void test_singleVehicleNotInSingleDistrict_returnsFalse() {
        districts3.add(vehicles.get(0).getDistrict());

        DistrictKeywordsPredicate p3 = new DistrictKeywordsPredicate(districts3);

        assertFalse(p3.test(vehicles.get(1)));
    }

    @Test
    public void test_singleVehicleNotInAnyOfTheMultipleDistricts_returnsFalse() {
        for (int i = 0; i < vehicles.size(); i++) {
            if (i % 2 == 0) {
                districts2.add(vehicles.get(i).getDistrict());
            }
        }

        DistrictKeywordsPredicate p2 = new DistrictKeywordsPredicate(districts2);

        assertFalse(p2.test(vehicles.get(1)));
        assertFalse(p2.test(vehicles.get(3)));
    }
}
