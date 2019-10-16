package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.notif.Notif;

/**
 * A utility class containing an {@code AddressBook} object to be used in tests.
 */
public class TypicalAddressBook {

    /**
     * Returns an {@code AddressBook} with all the typical bodies, workers, fridges, and notifs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        for (Body body : TypicalBodies.getTypicalBodies()) {
            ab.addEntity(body);
        }

        for (Worker worker : TypicalWorkers.getTypicalWorkers()) {
            ab.addEntity(worker);
        }

        for (Fridge fridge : TypicalFridges.getTypicalFridges()) {
            ab.addEntity(fridge);
        }

        for (Notif notif : TypicalNotifs.getTypicalNotifs()) {
            ab.addNotif(notif);
        }

        return ab;
    }

}
