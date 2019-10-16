package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.spending.Spending;

/**
 * A utility class containing a list of {@code Spending} objects to be used in tests.
 */
public class TypicalSpendings {

    public static final Spending ALICE = new SpendingBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withDate("1/1/2019")
            .withTags("friends").build();
    public static final Spending BENSON = new SpendingBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withDate("2/1/2019")
            .withTags("owesMoney", "friends").build();
    public static final Spending CARL = new SpendingBuilder().withName("Carl Kurz").withDate("3/1/2019")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Spending DANIEL = new SpendingBuilder().withName("Daniel Meier").withDate("4/1/2019")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Spending ELLE = new SpendingBuilder().withName("Elle Meyer").withDate("5/1/2019")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Spending FIONA = new SpendingBuilder().withName("Fiona Kunz").withDate("6/1/2019")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Spending GEORGE = new SpendingBuilder().withName("George Best").withDate("7/1/2019")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Spending HOON = new SpendingBuilder().withName("Hoon Meier").withDate("8/1/2019")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Spending IDA = new SpendingBuilder().withName("Ida Mueller").withDate("9/1/2019")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Spending's details found in {@code CommandTestUtil}
    public static final Spending AMY = new SpendingBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Spending BOB = new SpendingBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalSpendings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical spendings.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Spending spending : getTypicalSpendings()) {
            ab.addSpending(spending);
        }
        return ab;
    }

    public static List<Spending> getTypicalSpendings() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
