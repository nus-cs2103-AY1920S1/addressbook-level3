package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISOPEN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISOPEN_BOB;
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

    public static final Eatery ALICE = new EateryBuilder().withName("Alice Pauline").withIsOpen(true)
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withCategory("Chinese")
            .withTags("friends").build();
    public static final Eatery BENSON = new EateryBuilder().withName("Benson Meier").withIsOpen(true)
            .withAddress("311, Clementi Ave 2, #02-25")
            .withCategory("Western")
            .withTags("owesMoney", "friends").build();
    public static final Eatery CARL = new EateryBuilder().withName("Carl Kurz").withIsOpen(true)
            .withAddress("wall street")
            .withCategory("Western").build();
    public static final Eatery DANIEL = new EateryBuilder().withName("Daniel Meier").withIsOpen(true)
            .withAddress("10th street")
            .withCategory("Western")
            .withTags("friends").build();
    public static final Eatery ELLE = new EateryBuilder().withName("Elle Meyer").withIsOpen(true)
            .withAddress("michegan ave")
            .withCategory("Western").build();
    public static final Eatery FIONA = new EateryBuilder().withName("Fiona Kunz").withIsOpen(true)
            .withAddress("little tokyo")
            .withCategory("Western").build();
    public static final Eatery GEORGE = new EateryBuilder().withName("George Best").withIsOpen(true)
            .withAddress("4th street")
            .withCategory("Western").build();

    // Manually added
    public static final Eatery HOON = new EateryBuilder().withName("Hoon Meier").withIsOpen(true)
            .withAddress("little india")
            .withCategory("Western").build();
    public static final Eatery IDA = new EateryBuilder().withName("Ida Mueller").withIsOpen(true)
            .withAddress("chicago ave")
            .withCategory("Western").build();

    // Manually added - Eatery's details found in {@code CommandTestUtil}
    public static final Eatery AMY = new EateryBuilder().withName(VALID_NAME_AMY).withIsOpen(VALID_ISOPEN_AMY)
            .withAddress(VALID_ADDRESS_AMY).withCategory(VALID_CATEGORY).withTags(VALID_TAG_FRIEND).build();
    public static final Eatery BOB = new EateryBuilder().withName(VALID_NAME_BOB).withIsOpen(VALID_ISOPEN_BOB)
            .withAddress(VALID_ADDRESS_BOB).withCategory(VALID_CATEGORY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEateries() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical eateries.
     */
    public static AddressBook getTypicalOpenAddressBook() {
        AddressBook ab = new AddressBook();
        for (Eatery eatery : getTypicalEateries()) {
            ab.addEatery(eatery);
            ab.toggle();
            ab.addEatery(eatery);
        }
        return ab;
    }

    public static AddressBook getTypicalCloseAddressBook() {
        AddressBook ab = new AddressBook();
        for (Eatery eatery : getTypicalEateries()) {
            Eatery closedEatery = new EateryBuilder(eatery).withIsOpen(false).build();
            ab.addEatery(closedEatery);
        }
        return ab;
    }

    public static List<Eatery> getTypicalEateries() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
