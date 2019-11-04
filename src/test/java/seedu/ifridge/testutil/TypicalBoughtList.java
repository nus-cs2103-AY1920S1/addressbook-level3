package seedu.ifridge.testutil;

import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_NAME_ORANGES;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_TAG_CARBS;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_TAG_VEGETABLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.food.GroceryItem;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalBoughtList {

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
    public static final GroceryItem AMY = new GroceryItemBuilder().withName(VALID_NAME_NUTS)
            .withTags(VALID_TAG_VEGETABLE).build();
    public static final GroceryItem BOB = new GroceryItemBuilder().withName(VALID_NAME_ORANGES)
            .withTags(VALID_TAG_CARBS, VALID_TAG_VEGETABLE)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBoughtList() {} // prevents instantiation

    /**
     * Returns an {@code GroceryList} with all the typical persons.
     */
    public static GroceryList getTypicalBoughtList() {
        GroceryList ab = new GroceryList();
        for (GroceryItem food : getTypicalPersons()) {
            ab.addGroceryItem(food);
        }
        return ab;
    }

    public static List<GroceryItem> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
