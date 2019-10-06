package seedu.address.testutil;

import seedu.address.model.order.Order;
import seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;
import seedu.address.testutil.TypicalCustomers.CUSTOMERTHREE;
import seedu.address.testutil.TypicalPhones.IPHONEONE;
import seedu.address.testutil.TypicalPhones.ANDROIDONE;
import seedu.address.testutil.TypicalPhones.IPHONETWO;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    //might move into commandtestutil later on
    private static final String DEFAULT_PRICE_1 = "$1231.12";
    private static final String DEFAULT_PRICE_2 = "$909";
    private static final String DEFAULT_PRICE_3 = "$500";
    private static final String DEFAULT_TAG_1 = "Urgent";
    private static final String DEFAULT_TAG_2 = "New";
    private static final String DEFAULT_TAG_3 = "Old";

    public static final Order ORDERONE = new OrderBuilder().withCustomer(CUSTOMERONE).withPhone(IPHONEONE)
            .withPrice(DEFAULT_PRICE_1).withTags(DEFAULT_TAG_1).build();

    public static final Order ORDERTWO = new OrderBuilder().withCustomer(CUSTOMERTWO).withPhone(ANDROIDONE)
            .withPrice(DEFAULT_PRICE_2).withTags(DEFAULT_TAG_2).build();

    public static final Order ORDERTHREE = new OrderBuilder().withCustomer(CUSTOMERTHREE).withPhone(IPHONETWO)
            .withPrice(DEFAULT_PRICE_3).withTags(DEFAULT_TAG_3).build();

}
