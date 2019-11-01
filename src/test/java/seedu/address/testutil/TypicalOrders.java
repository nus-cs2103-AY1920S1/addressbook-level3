package seedu.address.testutil;

import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTHREE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;
import static seedu.address.testutil.TypicalPhones.ANDROIDONE;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;
import static seedu.address.testutil.TypicalPhones.IPHONETWO;
import static seedu.address.testutil.TypicalPhones.IPHONEXR;
import static seedu.address.testutil.TypicalSchedules.FRIDAY_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.MONDAY_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.SCHEDULESTATFIVE;
import static seedu.address.testutil.TypicalSchedules.SCHEDULESTATFOUR;
import static seedu.address.testutil.TypicalSchedules.SCHEDULESTATONE;
import static seedu.address.testutil.TypicalSchedules.SCHEDULESTATSEVEN;
import static seedu.address.testutil.TypicalSchedules.SCHEDULESTATSIX;
import static seedu.address.testutil.TypicalSchedules.SCHEDULESTATTHREE;
import static seedu.address.testutil.TypicalSchedules.SCHEDULESTATTWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.model.DataBook;
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
    private static final String VALID_PRICE_HIGH = "$3000";

    public static final Order ORDERONE = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERONE)
            .withPhone(IPHONEONE).withPrice(DEFAULT_PRICE_1).withStatus(Status.SCHEDULED)
            .withSchedule(Optional.of(MONDAY_SCHEDULE)).withTags(DEFAULT_TAG_1).build();

    public static final Order ORDERTWO = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTWO)
            .withPhone(ANDROIDONE).withPrice(DEFAULT_PRICE_2).withStatus(Status.SCHEDULED)
            .withSchedule(Optional.of(FRIDAY_SCHEDULE)).withTags(DEFAULT_TAG_2).build();

    public static final Order ORDERTHREE = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTHREE)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_3).withStatus(Status.UNSCHEDULED)
            .withSchedule(Optional.empty()).withTags(DEFAULT_TAG_3).build();

    public static final Order VIPORDER = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(ALICE)
            .withPhone(IPHONEXR).withPrice(VALID_PRICE_HIGH).withTags(DEFAULT_TAG_2).withStatus(Status.UNSCHEDULED)
            .withSchedule(Optional.empty()).build();

    //1231.12 - 300
    public static final Order ORDERONESTATS = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERONE)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_1).withStatus(Status.COMPLETED)
            .withSchedule(Optional.of(SCHEDULESTATONE)).withTags(DEFAULT_TAG_1).build();
    //909 - 300
    public static final Order ORDERTWOSTATS = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTWO)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_2).withStatus(Status.COMPLETED)
            .withSchedule(Optional.of(SCHEDULESTATTWO)).withTags(DEFAULT_TAG_2).build();
    //500-300
    public static final Order ORDERTHREESTATS = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTHREE)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_3).withStatus(Status.COMPLETED)
            .withSchedule(Optional.of(SCHEDULESTATTHREE)).withTags(DEFAULT_TAG_3).build();

    public static final Order ORDERFOURSTATS = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTHREE)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_3).withStatus(Status.COMPLETED)
            .withSchedule(Optional.of(SCHEDULESTATFOUR)).withTags(DEFAULT_TAG_3).build();

    public static final Order ORDERFIVESTATS = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTHREE)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_3).withStatus(Status.COMPLETED)
            .withSchedule(Optional.of(SCHEDULESTATFIVE)).withTags(DEFAULT_TAG_3).build();

    public static final Order ORDERSIXSTATS = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTHREE)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_3).withStatus(Status.COMPLETED)
            .withSchedule(Optional.of(SCHEDULESTATSIX)).withTags(DEFAULT_TAG_3).build();

    public static final Order ORDERSEVENSTATS = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERTHREE)
            .withPhone(IPHONETWO).withPrice(DEFAULT_PRICE_3).withStatus(Status.COMPLETED)
            .withSchedule(Optional.of(SCHEDULESTATSEVEN)).withTags(DEFAULT_TAG_3).build();


    public static final Order VIPORDERSTATS = new OrderBuilder().withCustomer(ALICE).withPhone(IPHONEXR)
            .withPrice(VALID_PRICE_HIGH)
            .withStatus(Status.COMPLETED)
            .withSchedule(Optional.of(FRIDAY_SCHEDULE))
            .withTags(DEFAULT_TAG_2).build();


    public static final Order ORDER_JSON_TEST = new OrderBuilder().withId(UUID.randomUUID()).withCustomer(CUSTOMERONE)
            .withPhone(IPHONEONE).withPrice(DEFAULT_PRICE_1).withStatus(Status.SCHEDULED)
            .withSchedule(Optional.of(MONDAY_SCHEDULE)).withTags(DEFAULT_TAG_1).build();

    /**
     * Returns a {@code DataBook} with all the typical orders.
     */
    public static DataBook<Order> getTypicalOrderBook() {
        DataBook<Order> ob = new DataBook<>();
        for (Order o: getTypicalOrders()) {
            ob.add(o);
        }
        return ob;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ORDERONE, ORDERTWO, ORDERTHREE, VIPORDER));
    }


}
