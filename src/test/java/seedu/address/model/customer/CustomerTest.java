package seedu.address.model.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CustomerBuilder;

public class CustomerTest {

    private static final String VALID_NAME = "Hoon Meier";
    private static final String VALID_CONTACTNUMBER = "87652533";
    private static final String VALID_EMAIL = "stefan@example.com";
    private static final String VALID_TAG = "friendly";

    @Test
    public void isSameCustomer() {
        // same object -> returns true
        assertTrue(CUSTOMERONE.isSameCustomer(CUSTOMERONE));

        // null -> returns false
        assertFalse(CUSTOMERONE.isSameCustomer(null));

        // different phone and email -> returns false
        assertFalse(CUSTOMERONE.isSameCustomer(new CustomerBuilder(CUSTOMERONE).withContactNumber(VALID_CONTACTNUMBER)
                .withEmail(VALID_EMAIL).build()));

        // different name -> returns false
        assertFalse(CUSTOMERONE.isSameCustomer(new CustomerBuilder(CUSTOMERONE).withName(VALID_NAME).build()));

        // same name, same phone, different attributes -> returns true
        assertTrue(CUSTOMERONE.isSameCustomer(new CustomerBuilder(CUSTOMERONE).withEmail(VALID_EMAIL)
                .withTags(VALID_TAG).build()));

        // same name, same email, different attributes -> returns true
        assertTrue(CUSTOMERONE.isSameCustomer(new CustomerBuilder(CUSTOMERONE).withContactNumber(VALID_CONTACTNUMBER)
                .withTags(VALID_TAG).build()));

        // same name, same phone, same email, different attributes -> returns true
        assertTrue(CUSTOMERONE.isSameCustomer(new CustomerBuilder(CUSTOMERONE).withTags(VALID_TAG).build()));
    }

    @Test
    public void testEquals() {
        // same object -> returns true
        assertTrue(CUSTOMERONE.equals(CUSTOMERONE));

        // null -> returns false
        assertFalse(CUSTOMERONE.equals(null));

        // same data fields -> returns true
        assertTrue(CUSTOMERONE.equals(new CustomerBuilder(CUSTOMERONE).build()));

        // different type -> returns false
        assertFalse(CUSTOMERONE.equals(5));

        // different person -> returns false
        assertFalse(CUSTOMERONE.equals(CUSTOMERTWO));

        // different name -> returns false
        assertFalse(CUSTOMERONE.equals(new CustomerBuilder(CUSTOMERONE).withName(VALID_NAME).build()));

        // different phone -> returns false
        assertFalse(CUSTOMERONE.equals(new CustomerBuilder(CUSTOMERONE).withContactNumber(VALID_CONTACTNUMBER)
                .build()));

        // different email -> returns false
        assertFalse(CUSTOMERONE.equals(new CustomerBuilder(CUSTOMERONE).withEmail(VALID_EMAIL).build()));

        // different tags -> returns false
        assertFalse(CUSTOMERONE.equals(new CustomerBuilder(CUSTOMERONE).withTags(VALID_TAG).build()));
    }

}
