package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.entity.body.BodyStatus.PENDING_POLICE_REPORT;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.notif.Notif;

public class NotifCommandTest {
    private static final long ONE_SECOND = 1000;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_notifAcceptedByModel_addSuccessful() throws CommandException, InterruptedException {
        Notif notif = new Notif(ALICE);
        NotifCommand notifCommand = new NotifCommand(notif, 500, TimeUnit.MILLISECONDS);
        notifCommand.execute(model);

        Thread.sleep(ONE_SECOND);

        assertEquals(PENDING_POLICE_REPORT, notif.getBody().getBodyStatus());
    }
}
