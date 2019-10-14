package seedu.address.testutil;

import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTHREE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;
import static seedu.address.testutil.TypicalPhones.ANDROIDONE;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;
import static seedu.address.testutil.TypicalPhones.IPHONETWO;
import static seedu.address.testutil.TypicalSchedules.SCHEDULEONE;
import static seedu.address.testutil.TypicalSchedules.SCHEDULETWO;

import java.util.Optional;
import java.util.UUID;

import seedu.address.model.order.Order;
import seedu.address.model.order.Status;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    private static final String DEFAULT_PRICE_1 = "$1231.12";
    private static final String DEFAULT_PRICE_2 = "$909";
    private static final String DEFAULT_PRICE_3 = "$500";
    private static final String DEFAULT_TAG_1 = "Urgent";
    private static final String DEFAULT_TAG_2 = "New";
    private static final String DEFAULT_TAG_3 = "Old";

    public static final Order ORDERONE = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERONE)
            .withPhone(IPHONEONE).withPrice(DEFAULT_PRICE_1).withStatus(Status.UNSCHEDULED)
            .withSchedule(Optional.of(SCHEDULEONE)).withTags(DEFAULT_TAG_1).build();

    public static final Order ORDERTWO = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTWO)
            .withPhone(ANDROIDONE).withPrice(DEFAULT_PRICE_2).withStatus(Status.SCHEDULED)
            .withSchedule(Optional.of(SCHEDULETWO)).withTags(DEFAULT_TAG_2).build();

    public static final Order ORDERTHREE = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTHREE)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_3).withStatus(Status.COMPLETED)
            .withSchedule(Optional.empty()).withTags(DEFAULT_TAG_3).build();

}
