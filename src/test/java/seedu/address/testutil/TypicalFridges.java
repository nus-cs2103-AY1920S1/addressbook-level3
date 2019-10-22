package seedu.address.testutil;

import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;

import seedu.address.model.entity.fridge.Fridge;

//@author arjavibahety
/**
 * A utility class containing {@code Fridge} objects to be used in tests.
 */
public class TypicalFridges {

    public static final Fridge EMPTY_FRIDGE = new FridgeBuilder().build();
    public static final Fridge ALICE_FRIDGE = new FridgeBuilder().withBody(ALICE).build();
    public static final Fridge BOB_FRIDGE = new FridgeBuilder().withBody(BOB).build();

    private TypicalFridges() {} // prevents instantiation
}
