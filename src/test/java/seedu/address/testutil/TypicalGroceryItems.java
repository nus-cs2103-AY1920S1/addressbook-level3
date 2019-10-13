package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.GroceryItem;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalGroceryItems {

    public static final GroceryItem ALICE = new GroceryItemBuilder().withName("Alice Pauline")
            .withTags("friends").build();
    public static final GroceryItem BENSON = new GroceryItemBuilder().withName("Benson Meier")
            .withTags("owesMoney", "friends").build();
    public static final GroceryItem CARL = new GroceryItemBuilder().withName("Carl Kurz").build();
    public static final GroceryItem DANIEL = new GroceryItemBuilder().withName("Daniel Meier")
            .withTags("friends").build();
    public static final GroceryItem ELLE = new GroceryItemBuilder().withName("Elle Meyer")
            .build();
    public static final GroceryItem FIONA = new GroceryItemBuilder().withName("Fiona Kunz")
            .build();
    public static final GroceryItem GEORGE = new GroceryItemBuilder().withName("George Best")
            .build();

    // Manually added
    public static final GroceryItem HOON = (GroceryItem) new GroceryItemBuilder().withName("Hoon Meier")
            .build();
    public static final GroceryItem IDA = (GroceryItem) new GroceryItemBuilder().withName("Ida Mueller")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final GroceryItem AMY = new GroceryItemBuilder().withName(VALID_NAME_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final GroceryItem BOB = new GroceryItemBuilder().withName(VALID_NAME_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalGroceryItems() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (GroceryItem food : getTypicalPersons()) {
            ab.addPerson(food);
        }
        return ab;
    }

    public static List<GroceryItem> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
