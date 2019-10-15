package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.eatery.Eatery;

/**
 * A utility class containing a list of {@code Eatery} objects to be used in tests.
 */
public class TypicalEateries {

    public static final Eatery ALICE = new EateryBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withTags("friends").build();
    public static final Eatery BENSON = new EateryBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")

            .withTags("owesMoney", "friends").build();
    public static final Eatery CARL = new EateryBuilder().withName("Carl Kurz")
            .withAddress("wall street").build();
    public static final Eatery DANIEL = new EateryBuilder().withName("Daniel Meier")
            .withAddress("10th street").withTags("friends").build();
    public static final Eatery ELLE = new EateryBuilder().withName("Elle Meyer")
            .withAddress("michegan ave").build();
    public static final Eatery FIONA = new EateryBuilder().withName("Fiona Kunz")
            .withAddress("little tokyo").build();
    public static final Eatery GEORGE = new EateryBuilder().withName("George Best")
            .withAddress("4th street").build();

    // Manually added
    public static final Eatery HOON = new EateryBuilder().withName("Hoon Meier")
            .withAddress("little india").build();
    public static final Eatery IDA = new EateryBuilder().withName("Ida Mueller")
            .withAddress("chicago ave").build();

    // Manually added - Eatery's details found in {@code CommandTestUtil}
    public static final Eatery AMY = new EateryBuilder().withName(VALID_NAME_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Eatery BOB = new EateryBuilder().withName(VALID_NAME_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEateries() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical eateries.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Eatery eatery : getTypicalEateries()) {
            ab.addEatery(eatery);
        }
        return ab;
    }

    public static List<Eatery> getTypicalEateries() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
