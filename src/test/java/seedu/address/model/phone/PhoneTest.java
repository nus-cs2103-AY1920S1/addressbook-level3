package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PhoneBuilder;

class PhoneTest {

    private static final String VALID_IDENTITYNUMBER = "000000000000000";
    private static final String VALID_SERIALNUMBER = "12asd34";
    private static final String VALID_NAME = "iPhone 2";
    private static final String VALID_BRAND = "Huawei";
    private static final Capacity VALID_CAPACITY = Capacity.SIZE_8GB;
    private static final String VALID_COLOUR = "Vanta Black";
    private static final String VALID_COST = "$0";
    private static final String VALID_TAG = "1337code";

    @Test
    void isSamePhone() {
        // same object -> returns true
        assertTrue(IPHONEONE.isSameAs(IPHONEONE));

        // null -> returns false
        assertFalse(IPHONEONE.isSameAs(null));

        // different IMEI and serial number -> returns false
        assertFalse(IPHONEONE.isSamePhone(new PhoneBuilder(IPHONEONE)
                .withIdentityNumber(VALID_IDENTITYNUMBER)
                .withSerialNumber(VALID_SERIALNUMBER).build()));

        // different serial number and same IMEI -> returns true
        assertTrue(IPHONEONE.isSamePhone(new PhoneBuilder(IPHONEONE).build()));

        // different phone name -> returns true
        assertTrue(IPHONEONE.isSameAs(new PhoneBuilder(IPHONEONE).withName(VALID_NAME).build()));

        // different brand -> returns true
        assertTrue(IPHONEONE.isSameAs(new PhoneBuilder(IPHONEONE).withName(VALID_BRAND).build()));

        // different capacity -> returns true
        assertTrue(IPHONEONE.isSameAs(new PhoneBuilder(IPHONEONE).withCapacity(VALID_CAPACITY).build()));

        // different colour -> returns true
        assertTrue(IPHONEONE.isSameAs(new PhoneBuilder(IPHONEONE).withColour(VALID_COLOUR).build()));

        // different cost -> returns true
        assertTrue(IPHONEONE.isSameAs(new PhoneBuilder(IPHONEONE).withCost(VALID_COST).build()));

        // different tags -> returns true
        assertTrue(IPHONEONE.isSameAs(new PhoneBuilder(IPHONEONE).withTags(VALID_TAG).build()));
    }

    @Test
    void testEquals() {
        // same object -> equals
        assertEquals(IPHONEONE, IPHONEONE);

        // null -> not equals
        assertNotEquals(null, IPHONEONE);

        // different IMEI -> not equals
        assertNotEquals(IPHONEONE, new PhoneBuilder(IPHONEONE).withIdentityNumber(VALID_IDENTITYNUMBER).build());

        // different serial number -> not equals
        assertNotEquals(IPHONEONE, new PhoneBuilder(IPHONEONE).withSerialNumber(VALID_SERIALNUMBER).build());

        // different phone name -> not equals
        assertNotEquals(IPHONEONE, new PhoneBuilder(IPHONEONE).withName(VALID_NAME).build());

        // different brand -> not equals
        assertNotEquals(IPHONEONE, new PhoneBuilder(IPHONEONE).withBrand(VALID_BRAND).build());

        // different capacity -> not equals
        assertNotEquals(IPHONEONE, new PhoneBuilder(IPHONEONE).withCapacity(VALID_CAPACITY).build());

        // different colour -> not equals
        assertNotEquals(IPHONEONE, new PhoneBuilder(IPHONEONE).withColour(VALID_COLOUR).build());

        // different cost -> not equals
        assertNotEquals(IPHONEONE, new PhoneBuilder(IPHONEONE).withCost(VALID_COST).build());

        // different tags -> not equals
        assertNotEquals(IPHONEONE, new PhoneBuilder(IPHONEONE).withTags(VALID_TAG).build());
    }
}
