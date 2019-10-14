package seedu.address.testutil;

import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
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

    /**
     * Returns an {@code AddressBook} with all the typical bodies and workers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Fridge fridge : getTypicalFridges()) {
            ab.addEntity(fridge);
        }

        return ab;
    }

    public static List<Fridge> getTypicalFridges() {
        return new ArrayList<>(Arrays.asList(ALICE_FRIDGE, BOB_FRIDGE, EMPTY_FRIDGE));
    }
}
