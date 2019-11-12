package seedu.eatme.testutil;

import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_NO_PREFIX_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_CATEGORY_NO_PREFIX;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ISOPEN_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ISOPEN_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_NO_PREFIX_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_CHEAP;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_NICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.eatme.model.EateryList;
import seedu.eatme.model.eatery.Eatery;

/**
 * A utility class containing a list of {@code Eatery} objects to be used in tests.
 */
public class TypicalEateries {

    public static final Eatery POPEYES = new EateryBuilder().withName("Popeyes").withIsOpen(true)
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withCategory("Western")
            .withTags("fastfood").build();
    public static final Eatery MAC = new EateryBuilder().withName("McDonald").withIsOpen(true)
            .withAddress("311, Clementi Ave 2, #02-25")
            .withCategory("Western")
            .withTags("fastfood").build();
    public static final Eatery MOS = new EateryBuilder().withName("MOS Burger").withIsOpen(true)
            .withAddress("wall street")
            .withCategory("Western").build();
    public static final Eatery KFC = new EateryBuilder().withName("Kentucky Fried Chicken").withIsOpen(true)
            .withAddress("10th street")
            .withCategory("Western")
            .withTags("fastfood").build();
    public static final Eatery TEXAS = new EateryBuilder().withName("Texas Chicken").withIsOpen(true)
            .withAddress("michegan ave")
            .withCategory("Western").build();
    public static final Eatery BURGER = new EateryBuilder().withName("Burger King").withIsOpen(true)
            .withAddress("little tokyo")
            .withCategory("Western").build();
    public static final Eatery PIZZA = new EateryBuilder().withName("Pizza Hut").withIsOpen(true)
            .withAddress("4th street")
            .withCategory("Western").build();

    // Manually added
    public static final Eatery PIZZAHUT = new EateryBuilder().withName("Pizza Hut").withIsOpen(true)
            .withAddress("little india")
            .withCategory("Western").build();
    public static final Eatery BURGERKING = new EateryBuilder().withName("Burger King").withIsOpen(true)
            .withAddress("chicago ave")
            .withCategory("Western").build();

    // Manually added - Eatery's details found in {@code CommandTestUtil}
    public static final Eatery MCDONALD = new EateryBuilder().withName(VALID_NAME_NO_PREFIX_MAC)
            .withIsOpen(VALID_ISOPEN_MAC)
            .withAddress(VALID_ADDRESS_NO_PREFIX_MAC).withCategory(VALID_CATEGORY_NO_PREFIX)
            .withTags(VALID_TAG_NO_PREFIX_CHEAP).build();
    public static final Eatery KENTUCKY = new EateryBuilder().withName(VALID_NAME_NO_PREFIX_KFC)
            .withIsOpen(VALID_ISOPEN_KFC)
            .withAddress(VALID_ADDRESS_NO_PREFIX_KFC).withCategory(VALID_CATEGORY_NO_PREFIX)
            .withTags(VALID_TAG_NO_PREFIX_CHEAP, VALID_TAG_NO_PREFIX_NICE)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEateries() {} // prevents instantiation

    /**
     * Returns an {@code eateryList} with all the typical eateries.
     */
    public static EateryList getTypicalOpenEateryList() {
        EateryList ab = new EateryList();
        for (Eatery eatery : getTypicalEateries()) {
            ab.addEatery(eatery);
            ab.toggle();
            ab.addEatery(eatery);
        }
        return ab;
    }

    public static EateryList getTypicalCloseEateryList() {
        EateryList ab = new EateryList();
        for (Eatery eatery : getTypicalEateries()) {
            Eatery closedEatery = new EateryBuilder(eatery).withIsOpen(false).build();
            ab.addEatery(closedEatery);
        }
        return ab;
    }

    public static EateryList getTypicalReviewEateryList() {
        EateryList ab = new EateryList();
        for (Eatery eatery : getTypicalEateries()) {
            Eatery eateryWithReviews = new EateryBuilder(eatery).withReviews().buildWithReviews();
            ab.addEatery(eateryWithReviews);
        }
        return ab;
    }

    public static List<Eatery> getTypicalEateries() {
        return new ArrayList<>(Arrays.asList(POPEYES, MAC, MOS, KFC, TEXAS, BURGER, PIZZA));
    }
}
