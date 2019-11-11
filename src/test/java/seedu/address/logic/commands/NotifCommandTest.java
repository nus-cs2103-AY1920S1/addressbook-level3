package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.entity.body.BodyStatus.CONTACT_POLICE;
import static seedu.address.model.entity.body.BodyStatus.PENDING_CLAIM;
import static seedu.address.testutil.TypicalNotifs.ALICE_NOTIF;
import static seedu.address.testutil.TypicalNotifs.BOB_NOTIF;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;
import seedu.address.model.entity.body.Body;
import seedu.address.model.notif.Notif;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.BodyBuilder;
import seedu.address.ui.GuiUnitTest;
import systemtests.SystemTestSetupHelper;

//@@author arjavibahety

public class NotifCommandTest extends GuiUnitTest {

    private static final long ONE_SECOND = 1000;

    @TempDir
    public Path testFolder;

    private Model model;

    @BeforeEach
    public void setup() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SystemTestSetupHelper.initialize();
    }

    @Test
    public void execute_notifChangesBodyStatus_successful() throws CommandException, InterruptedException {
        model.addEntity(BOB_NOTIF.getBody());
        NotifCommand notifCommand = new NotifCommand(BOB_NOTIF, 500, TimeUnit.MILLISECONDS);
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(testFolder.resolve("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(testFolder.resolve("prefs"));
        notifCommand.setStorage(new StorageManager(addressBookStorage, userPrefsStorage));
        notifCommand.execute(model);

        Thread.sleep(ONE_SECOND);
        assertEquals(Optional.of(CONTACT_POLICE), BOB_NOTIF.getBody().getBodyStatus());
    }

    @Test
    public void execute_notifNotChangesBodyStatus_successful() throws CommandException, InterruptedException {
        NotifCommand notifCommand = new NotifCommand(ALICE_NOTIF, 500, TimeUnit.MILLISECONDS);
        notifCommand.execute(model);

        Thread.sleep(ONE_SECOND);

        assertEquals(Optional.of(PENDING_CLAIM), ALICE_NOTIF.getBody().getBodyStatus());
    }

    @Test
    public void equals() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Body body = new BodyBuilder().build();
        Notif notif = new Notif(body);
        NotifCommand notifCommand = new NotifCommand(notif, 1, TimeUnit.MILLISECONDS);

        // same values -> returns true
        NotifCommand notifCommandCopy = new NotifCommand(notif, 1, TimeUnit.MILLISECONDS);
        assertTrue(notifCommand.equals(notifCommandCopy));

        // same object -> return true
        assertTrue(notifCommand.equals(notifCommand));

        // null -> return false
        assertFalse(notifCommand.equals(null));

        // different types -> returns false
        assertFalse(notifCommand.equals(new ExitCommand()));

        // different notifs -> return false
        assertFalse(notifCommand.equals(new NotifCommand(ALICE_NOTIF, 1, TimeUnit.MILLISECONDS)));

        // different period -> return false
        assertFalse(notifCommand.equals(new NotifCommand(notif, 2, TimeUnit.MILLISECONDS)));

        // different timeUnit -> return false
        assertFalse(notifCommand.equals(new NotifCommand(notif, 1, TimeUnit.SECONDS)));

        assertEquals(notifCommand.hashCode(), notifCommandCopy.hashCode());
    }
}
//@@author
