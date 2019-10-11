package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTHREE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;
import static seedu.address.testutil.TypicalOrders.ORDERONE;
import static seedu.address.testutil.TypicalPhones.IPHONETWO;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.customer.Customer;
import seedu.address.model.phone.Phone;
import seedu.address.testutil.OrderBuilder;

public class OrderTest {

    private static final UUID VALID_ID = UUID.randomUUID();
    private static final Customer VALID_CUSTOMER = CUSTOMERTWO;
    private static final Phone VALID_PHONE = IPHONETWO;
    private static final String VALID_PRICE = "$1021";
    private static final Status VALID_STATUS = Status.CANCELLED;
    private static final String VALID_TAG = "Cancelled";

    @Test
    public void isSameOrder() {
        // same object -> returns true
        assertTrue(ORDERONE.isSameOrder(ORDERONE));

        // null -> returns false
        assertFalse(ORDERONE.isSameOrder(null));

        // different id -> returns false
        assertFalse(ORDERONE.isSameOrder(new OrderBuilder(ORDERONE).withId(VALID_ID).build()));
    }

    @Test
    public void testEquals() {
        // same object -> equals
        assertEquals(ORDERONE, ORDERONE);

        // null -> returns false
        assertNotEquals(null, ORDERONE);

        // different id -> returns false
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withId(VALID_ID).build());

        // different customer -> returns false
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withCustomer(VALID_CUSTOMER).build());

        // different phone -> returns false
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withPhone(VALID_PHONE).build());

        // different price -> returns false
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withPrice(VALID_PRICE).build());

        // different status-> returns false
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withStatus(VALID_STATUS).build());

        // different tags -> returns false
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withTags(VALID_TAG).build());
    }
}
