package seedu.address.testutil;

import seedu.address.model.customer.Customer;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {

    // to be moved into some contants class for easier usage by other classes
    public static final String DEFAULT_NAME_1 = "Alice Pauline";
    public static final String DEFAULT_NAME_2 = "Benson Meier";
    public static final String DEFAULT_NAME_3 = "Hoon Meier";
    public static final String DEFAULT_CONTACTNUMBER_1 = "94351253";
    public static final String DEFAULT_CONTACTNUMBER_2 = "95352563";
    public static final String DEFAULT_CONTACTNUMBER_3 = "87652533";
    public static final String DEFAULT_EMAIL_1 = "alice@example.com";
    public static final String DEFAULT_EMAIL_2 = "johnd@example.com";
    public static final String DEFAULT_EMAIL_3 = "stefan@example.com";
    public static final String DEFAULT_TAG_1 = "frequent";
    public static final String DEFAULT_TAG_2 = "new";
    public static final String DEFAULT_TAG_3 = "friendly";

    public static final Customer CUSTOMERONE = new CustomerBuilder().withName(DEFAULT_NAME_1)
            .withContactNumber(DEFAULT_CONTACTNUMBER_1).withEmail(DEFAULT_EMAIL_1).withTags(DEFAULT_TAG_1).build();

    public static final Customer CUSTOMERTWO = new CustomerBuilder().withName(DEFAULT_NAME_2)
            .withContactNumber(DEFAULT_CONTACTNUMBER_2).withEmail(DEFAULT_EMAIL_2).withTags(DEFAULT_TAG_2).build();

    public static final Customer CUSTOMERTHREE = new CustomerBuilder().withName(DEFAULT_NAME_3)
            .withContactNumber(DEFAULT_CONTACTNUMBER_3).withEmail(DEFAULT_EMAIL_3).withTags(DEFAULT_TAG_3).build();

}
