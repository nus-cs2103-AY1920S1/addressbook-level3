package seedu.address.testutil;

import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTHREE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;
import static seedu.address.testutil.TypicalPhones.ANDROIDONE;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;
import static seedu.address.testutil.TypicalPhones.IPHONETWO;
import static seedu.address.testutil.TypicalPhones.IPHONEXR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import seedu.address.model.OrderBook;
import seedu.address.model.order.Order;

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

    public static final Order ORDERONE = new OrderBuilder().withCustomer(CUSTOMERONE).withPhone(IPHONEONE)
            .withPrice(DEFAULT_PRICE_1).withTags(DEFAULT_TAG_1).build();

    public static final Order ORDERTWO = new OrderBuilder().withCustomer(CUSTOMERTWO).withPhone(ANDROIDONE)
            .withPrice(DEFAULT_PRICE_2).withTags(DEFAULT_TAG_2).build();

    public static final Order ORDERTHREE = new OrderBuilder().withCustomer(CUSTOMERTHREE).withPhone(IPHONETWO)
            .withPrice(DEFAULT_PRICE_3).withTags(DEFAULT_TAG_3).build();

    public static final Order VIPORDER = new OrderBuilder().withCustomer(ALICE).withPhone(IPHONEXR)
            .withPrice(VALID_PRICE_HIGH).withTags(DEFAULT_TAG_2).build();

    /**
     * Returns an {@code Book} with all the typical orders.
     */
    public static OrderBook getTypicalOrderBook() {
        OrderBook ob = new OrderBook();
        for (Order o: getTypicalOrders()) {
            ob.addOrder(o);
        }
        return ob;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ORDERONE, ORDERTWO, ORDERTHREE, VIPORDER));
    }

}
