package seedu.address.model.phone;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.PhoneBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;

class PhoneTest {

    private static final String VALID_NAME = "iPhone 2";
    private static final String VALID_BRAND = "Huawei";
    private static final String VALID_COLOUR = "Vanta Black";
    private static final String VALID_COST = "$0";
    private static final String VALID_TAG = "1337code";

    @Test
    void isSamePhone() {
        // same object -> returns true
        assertTrue(IPHONEONE.isSamePhone(IPHONEONE));

        // clone -> returns true
        try {
            assertTrue(IPHONEONE.isSamePhone((Phone) IPHONEONE.clone()));
        } catch (CloneNotSupportedException e) {
            fail();
        }

        // null -> returns false
        assertFalse(IPHONEONE.isSamePhone(null));

        // different id -> returns false
        assertFalse(IPHONEONE.isSamePhone(new PhoneBuilder(IPHONEONE).build()));
    }

    @Test
    void testEquals() {
        // same object -> returns true
        assertTrue(IPHONEONE.equals(IPHONEONE));

        // clone -> returns true
        try {
            assertTrue(IPHONEONE.equals(IPHONEONE.clone()));
        } catch (CloneNotSupportedException e) {
            fail();
        }

        // null -> returns false
        assertFalse(IPHONEONE.equals(null));

        // same data fields -> returns true
        assertTrue(IPHONEONE.equals(new PhoneBuilder(IPHONEONE).build()));

        // different name -> returns false
        assertFalse(IPHONEONE.equals(new PhoneBuilder(IPHONEONE).withName(VALID_NAME).build()));

        // different brand -> returns false
        assertFalse(IPHONEONE.equals(new PhoneBuilder(IPHONEONE).withBrand(VALID_BRAND).build()));

        // different capacity -> returns false
        assertFalse(IPHONEONE.equals(new PhoneBuilder(IPHONEONE).withCapacity(Capacity.SIZE_8GB).build()));

        // different colour -> returns false
        assertFalse(IPHONEONE.equals(new PhoneBuilder(IPHONEONE).withColour(VALID_COLOUR).build()));

        // different cost -> returns false
        assertFalse(IPHONEONE.equals(new PhoneBuilder(IPHONEONE).withCost(VALID_COST).build()));

        // different tags -> returns false
        assertFalse(IPHONEONE.equals(new PhoneBuilder(IPHONEONE).withTags(VALID_TAG).build()));
    }
}
