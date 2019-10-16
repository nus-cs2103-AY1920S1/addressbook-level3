package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.entity.body.BodyStatus.PENDING_CLAIM;
import static seedu.address.model.entity.body.BodyStatus.PENDING_POLICE_REPORT;
import static seedu.address.testutil.TypicalNotifs.ALICE_NOTIF;
import static seedu.address.testutil.TypicalNotifs.BOB_NOTIF;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class NotifCommandTest {
    private static final long ONE_SECOND = 1000;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_notifChangesBodyStatus_changeSuccessful() throws CommandException, InterruptedException {
        NotifCommand notifCommand = new NotifCommand(BOB_NOTIF, 500, TimeUnit.MILLISECONDS);
        notifCommand.execute(model);

        Thread.sleep(ONE_SECOND);

        assertEquals(PENDING_POLICE_REPORT, BOB_NOTIF.getBody().getBodyStatus());
    }

    @Test
    public void execute_notifNotChangesBodyStatus_notChangeSuccessful() throws CommandException, InterruptedException {
        NotifCommand notifCommand = new NotifCommand(ALICE_NOTIF, 500, TimeUnit.MILLISECONDS);
        notifCommand.execute(model);

        Thread.sleep(ONE_SECOND);

        assertEquals(PENDING_CLAIM, ALICE_NOTIF.getBody().getBodyStatus());
    }

}
