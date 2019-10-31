package seedu.address.testutil;

import java.util.LinkedList;
import java.util.List;

import seedu.address.model.person.Slot;

/**
 * Provides sample slot.
 */
public class SampleSlot {
    /**
     * Returns sample slots for the sample graph one.
     */
    public static List<Slot> getSampleSlotsForGraph1() {
        Slot slot1 = new Slot("26/10/2019", "18:00", "18:30");
        Slot slot2 = new Slot("26/10/2019", "18:30", "19:00");
        Slot slot3 = new Slot("27/10/2019", "20:00", "20:30");
        Slot slot4 = new Slot("27/10/2019", "20:30", "21:00");
        Slot slot5 = new Slot("28/10/2019", "19:00", "19:30");

        List<Slot> slots = new LinkedList<>();
        slots.add(slot1);
        slots.add(slot2);
        slots.add(slot3);
        slots.add(slot4);
        slots.add(slot5);

        return slots;
    }
}
