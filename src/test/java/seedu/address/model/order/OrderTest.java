package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalOrders.ORDERONE;
import static seedu.address.testutil.TypicalOrders.ORDERTWO;
import seedu.address.testutil.TypicalCustomers.CUSTOMERTHREE;
import seedu.address.testutil.TypicalPhones.IPHONETWO;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderTest {

    private static final String VALID_PRICE = "$1021";
    private static final Status VALID_STATUS = Status.CANCELLED;
    private static final String VALID_TAG = "Buyer cancelled";

    @Test
    public void isSameOrder() {
        // same object -> returns true
        assertTrue(ORDERONE.isSameOrder(ORDERONE));

        // null -> returns false
        assertFalse(ORDERONE.isSameOrder(null));

        // different id -> returns false
        assertFalse(ORDERONE.isSameOrder(ORDERTWO));
    }

    @Test
    public void testEquals() {
        // same object -> returns true
        assertTrue(ORDERONE.equals(ORDERONE));

        // null -> returns false
        assertFalse(ORDERONE.equals(null));

        // same data fields -> returns true
        assertTrue(ORDERONE.equals(new OrderBuilder(ORDERONE).build()));

        // different customer -> returns false
        assertFalse(ORDERONE.equals(new OrderBuilder(ORDERONE).withCustomer(CUSTOMERTHREE).build()));

        // different phone -> returns false
        assertFalse(ORDERONE.equals(new OrderBuilder(ORDERONE).withPhone(IPHONETWO).build()));

        // different price -> returns false
        assertFalse(ORDERONE.equals(new OrderBuilder(ORDERONE).withPrice(VALID_PRICE).build()));

        // different status-> returns false
        assertFalse(ORDERONE.equals(new OrderBuilder(ORDERONE).withStatus(VALID_STATUS).build()));

        // different tags -> returns false
        assertFalse(ORDERONE.equals(new OrderBuilder(ORDERONE).withTags(VALID_TAG).build()));
    }
}
