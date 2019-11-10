package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DataBook;
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

    public static final Customer ALICE = new CustomerBuilder().withName("Alice Lim")
            .withContactNumber("98123459")
            .withEmail("alice@example.com")
            .withTags("regular").build();
    public static final Customer BENSON = new CustomerBuilder().withName("Benson Meier")
            .withContactNumber("98765432")
            .withEmail("johnd@example.com")
            .withTags("new", "friends").build();
    public static final Customer CARL = new CustomerBuilder().withName("Carl Kurz")
            .withContactNumber("95352563")
            .withEmail("heinz@example.com").build();
    public static final Customer DANIEL = new CustomerBuilder().withName("Daniel Meier")
            .withContactNumber("87652533")
            .withEmail("cornelia@example.com")
            .withTags("friends").build();
    public static final Customer ELLE = new CustomerBuilder().withName("Elle Meyer")
            .withContactNumber("94822243")
            .withEmail("werner@example.com").build();
    public static final Customer FIONA = new CustomerBuilder().withName("Fiona Kunz")
            .withContactNumber("94824272")
            .withEmail("lydia@example.com").build();
    public static final Customer GEORGE = new CustomerBuilder().withName("George Best")
            .withContactNumber("94824423")
            .withEmail("anna@example.com").build();
    /**
     * Returns a {@code DataBook} with all the typical customers.
     */
    public static DataBook<Customer> getTypicalCustomerBook() {
        DataBook<Customer> cb = new DataBook<>();
        for (Customer c: getTypicalCustomers()) {
            cb.add(c);
        }
        return cb;
    }

    public static List<Customer> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
