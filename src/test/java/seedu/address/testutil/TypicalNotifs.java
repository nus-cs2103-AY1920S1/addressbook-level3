package seedu.address.testutil;

import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.notif.Notif;

/**
 * A utility class containing a list of {@code notifs} objects to be used in tests.
 */
public class TypicalNotifs {

    public static final Notif ALICE_NOTIF = new NotifBuilder().withBody(ALICE).build();
    public static final Notif BOB_NOTIF = new NotifBuilder().withBody(BOB).build();

    private TypicalNotifs() {} // prevents instantiation

    public static List<Notif> getTypicalNotifs() {
        return new ArrayList<>(Arrays.asList(ALICE_NOTIF, BOB_NOTIF));
    }
}
