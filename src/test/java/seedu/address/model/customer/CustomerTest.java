package seedu.address.model.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CustomerBuilder;

class CustomerTest {

    private static final String VALID_NAME = "Hoon Meier";
    private static final String VALID_CONTACTNUMBER = "87652533";
    private static final String VALID_EMAIL = "stefan@example.com";
    private static final String VALID_TAG = "friendly";

    @Test
    void isSameCustomer() {
        // same object -> returns true
        assertTrue(CUSTOMERONE.isSameAs(CUSTOMERONE));

        // null -> returns false
        assertFalse(CUSTOMERONE.isSameAs(null));

        // different phone and email -> returns false
        assertFalse(CUSTOMERONE.isSameAs(new CustomerBuilder(CUSTOMERONE).withContactNumber(VALID_CONTACTNUMBER)
                .withEmail(VALID_EMAIL).build()));

        // different name -> returns false
        assertFalse(CUSTOMERONE.isSameAs(new CustomerBuilder(CUSTOMERONE).withName(VALID_NAME).build()));

        // same name, same phone, different attributes -> returns true
        assertTrue(CUSTOMERONE.isSameAs(new CustomerBuilder(CUSTOMERONE).withEmail(VALID_EMAIL)
                .withTags(VALID_TAG).build()));

        // same name, same email, different attributes -> returns true
        assertTrue(CUSTOMERONE.isSameAs(new CustomerBuilder(CUSTOMERONE).withContactNumber(VALID_CONTACTNUMBER)
                .withTags(VALID_TAG).build()));

        // same name, same phone, same email, different attributes -> returns true
        assertTrue(CUSTOMERONE.isSameAs(new CustomerBuilder(CUSTOMERONE).withTags(VALID_TAG).build()));
    }

    @Test
    void testEquals() {
        // same object -> equals
        assertEquals(CUSTOMERONE, CUSTOMERONE);

        // null -> not equals
        assertNotEquals(null, CUSTOMERONE);

        // same data fields -> not equals
        assertEquals(CUSTOMERONE, new CustomerBuilder(CUSTOMERONE).build());

        // different type -> not equals
        assertNotEquals(5, CUSTOMERONE);

        // different person -> not equals
        assertNotEquals(CUSTOMERONE, CUSTOMERTWO);

        // different name -> not equals
        assertNotEquals(CUSTOMERONE, new CustomerBuilder(CUSTOMERONE).withName(VALID_NAME).build());

        // different phone -> not equals
        assertNotEquals(CUSTOMERONE, new CustomerBuilder(CUSTOMERONE).withContactNumber(VALID_CONTACTNUMBER)
                .build());

        // different email -> not equals
        assertNotEquals(CUSTOMERONE, new CustomerBuilder(CUSTOMERONE).withEmail(VALID_EMAIL).build());

        // different tags -> not equals
        assertNotEquals(CUSTOMERONE, new CustomerBuilder(CUSTOMERONE).withTags(VALID_TAG).build());
    }

}
