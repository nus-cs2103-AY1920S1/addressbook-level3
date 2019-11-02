package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.vehicle.District;

public class NewCommandTest {

    @Test
    public void equals() {
        // control
        NewCommand nc1 = new NewCommand(new District(1), true, 0);

        // differ by district
        NewCommand nc2 = new NewCommand(new District(2), true, 0);

        // differ by auto, no index specified
        NewCommand nc3 = new NewCommand(new District(20), false, -1);

        // differ by auto, index = 1
        NewCommand nc4 = new NewCommand(new District(20), false, 1);

        // everything same but create different draft
        NewCommand nc5 = new NewCommand(new District(1), true, 0);

        // same object -> returns true
        assertTrue(nc1.equals(nc1));

        // different district -> return false
        assertFalse(nc1.equals(nc2));

        // different auto, index not specified
        assertFalse(nc2.equals(nc3));

        // different by auto, index specified
        assertFalse(nc2.equals(nc4));

        // different district and auto, no index specified -> return false
        assertFalse(nc1.equals(nc3));

        // different district, isAuto and indexOfV -> return false
        assertFalse(nc1.equals(nc4));
    }
}
