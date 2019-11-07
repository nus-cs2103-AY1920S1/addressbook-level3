package seedu.address.notif;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalNotifs.ALICE_NOTIF;
import static seedu.address.testutil.TypicalNotifs.BOB_NOTIF;

import org.junit.jupiter.api.Test;

import seedu.address.model.notif.Notif;
import seedu.address.testutil.NotifBuilder;

//@@author arjavibahety
public class NotifTest {

    @Test
    public void isSameNotif() {
        assertTrue(ALICE_NOTIF.isSameNotif(ALICE_NOTIF));
        assertFalse(ALICE_NOTIF.isSameNotif(null));
        assertFalse(BOB_NOTIF.isSameNotif(ALICE_NOTIF));

        // Not equal because Body is different
        Notif editedNotif = new NotifBuilder(BOB).withBody(ALICE).build();
        assertFalse(BOB_NOTIF.isSameNotif(editedNotif));
    }

    @Test
    public void isSameBody() {
        Notif aliceNotif = new NotifBuilder(ALICE).build();
        Notif bobNotif = new NotifBuilder(BOB).build();
        assertTrue(aliceNotif.getBody() == ALICE);
        assertFalse(aliceNotif.getBody() == BOB);
        assertFalse(aliceNotif.getBody() == bobNotif.getBody());
    }
}
//@@author
