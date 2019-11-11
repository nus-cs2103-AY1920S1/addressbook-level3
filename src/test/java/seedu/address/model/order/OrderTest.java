package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;
import static seedu.address.testutil.TypicalOrders.ORDERONE;
import static seedu.address.testutil.TypicalPhones.IPHONETWO;
import static seedu.address.testutil.TypicalSchedules.FRIDAY_SCHEDULE;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.customer.Customer;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.OrderBuilder;

class OrderTest {

    private static final UUID VALID_ID = UUID.randomUUID();
    private static final Customer VALID_CUSTOMER = CUSTOMERTWO;
    private static final Phone VALID_PHONE = IPHONETWO;
    private static final String VALID_PRICE = "$1021";
    private static final Status VALID_STATUS = Status.CANCELLED;
    private static final Schedule VALID_SCHEDULE = FRIDAY_SCHEDULE;
    private static final String VALID_TAG = "Cancelled";

    @Test
    void isSameOrder() {
        // same object -> returns true
        assertTrue(ORDERONE.isSameAs(ORDERONE));

        // null -> returns false
        assertFalse(ORDERONE.isSameAs(null));

        // different id -> returns true
        assertTrue(ORDERONE.isSameAs(new OrderBuilder(ORDERONE).withId(VALID_ID).build()));

        // different customer -> returns true
        assertTrue(ORDERONE.isSameAs(new OrderBuilder(ORDERONE).withCustomer(VALID_CUSTOMER).build()));

        // different phone -> returns false
        assertFalse(ORDERONE.isSameAs(new OrderBuilder(ORDERONE).withPhone(VALID_PHONE).build()));

        // different price -> returns true
        assertTrue(ORDERONE.isSameAs(new OrderBuilder(ORDERONE).withPrice(VALID_PRICE).build()));

        // different status -> returns true
        assertTrue(ORDERONE.isSameAs(new OrderBuilder(ORDERONE).withStatus(VALID_STATUS).build()));

        // different schedule -> returns true
        assertTrue(ORDERONE.isSameAs(new OrderBuilder(ORDERONE)
                .withSchedule(Optional.of(VALID_SCHEDULE)).build()));

        // different tags -> returns true
        assertTrue(ORDERONE.isSameAs(new OrderBuilder(ORDERONE).withTags(VALID_TAG).build()));
    }

    @Test
    void testEquals() {
        // same object -> equals
        assertEquals(ORDERONE, ORDERONE);

        // null -> not equals
        assertNotEquals(null, ORDERONE);

        // different id -> not equals
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withId(VALID_ID).build());

        // different customer -> not equals
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withCustomer(VALID_CUSTOMER).build());

        // different phone -> not equals
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withPhone(VALID_PHONE).build());

        // different price -> not equals
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withPrice(VALID_PRICE).build());

        // different status -> not equals
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withStatus(VALID_STATUS).build());

        // different schedule -> not equals
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withSchedule(Optional.of(VALID_SCHEDULE)).build());

        // different tags -> not equals
        assertNotEquals(ORDERONE, new OrderBuilder(ORDERONE).withTags(VALID_TAG).build());
    }
}
