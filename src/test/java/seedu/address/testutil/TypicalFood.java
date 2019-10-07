package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.food.Food;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalFood {

    public static final Food ALICE = new FoodBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPrice("94351253")
            .withTags("friends").build();
    public static final Food BENSON = new FoodBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPrice("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Food CARL = new FoodBuilder().withName("Carl Kurz").withPrice("95352563")
            .withEmail("heinz@example.com").build();
    public static final Food DANIEL = new FoodBuilder().withName("Daniel Meier").withPrice("87652533")
            .withEmail("cornelia@example.com").withTags("friends").build();
    public static final Food ELLE = new FoodBuilder().withName("Elle Meyer").withPrice("9482224")
            .withEmail("werner@example.com").build();
    public static final Food FIONA = new FoodBuilder().withName("Fiona Kunz").withPrice("9482427")
            .withEmail("lydia@example.com").build();
    public static final Food GEORGE = new FoodBuilder().withName("George Best").withPrice("9482442")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Food HOON = new FoodBuilder().withName("Hoon Meier").withPrice("8482424")
            .withEmail("stefan@example.com").build();
    public static final Food IDA = new FoodBuilder().withName("Ida Mueller").withPrice("8482131")
            .withEmail("hans@example.com").build();

    // Manually added - Food's details found in {@code CommandTestUtil}
    public static final Food AMY = new FoodBuilder().withName(VALID_NAME_AMY).withPrice(VALID_PRICE_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Food BOB = new FoodBuilder().withName(VALID_NAME_BOB).withPrice(VALID_PRICE_BOB)
            .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFood() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical foods.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Food food : getTypicalFood()) {
            ab.addFood(food);
        }
        return ab;
    }

    public static List<Food> getTypicalFood() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
