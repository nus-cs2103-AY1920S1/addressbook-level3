package seedu.jarvis.testutil.cca;

import seedu.jarvis.model.cca.Equipment;

/**
 * A utility class containing a list of {@code Equipment} objects to be used in tests.
 */
public class TypicalEquipments {

    public static final Equipment PADDLE = new Equipment("paddle");
    public static final Equipment BOAT = new Equipment("boat");
    public static final Equipment GUITAR = new Equipment("guitar");

    private TypicalEquipments() {} // prevents instantiation
}
