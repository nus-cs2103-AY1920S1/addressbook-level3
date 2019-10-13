package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_BORROWER;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.borrower.Borrower;
import seedu.address.testutil.BorrowerBuilder;

public class RegisterCommandTest {

    @Test
    public void constructor_nullBorrower_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RegisterCommand(null));
    }

    @Test
    public void execute_borrowerAcceptedByModel_addSuccessful() throws Exception {
        Model modelManager = new ModelManager();
        Borrower validBorrower = new BorrowerBuilder().actualBuild();

        CommandResult commandResult = new RegisterCommand(validBorrower).execute(modelManager);

        assertEquals(String.format(RegisterCommand.MESSAGE_SUCCESS, validBorrower), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBorrower), modelManager.getBorrowerRecords().getBorrowerList());
    }

    @Test
    public void execute_duplicateBorrower_throwsCommandException() throws CommandException {
        Model modelManager = new ModelManager();

        Borrower validBorrower = new BorrowerBuilder().actualBuild();
        RegisterCommand registerCommand = new RegisterCommand(validBorrower);
        registerCommand.execute(modelManager);
        RegisterCommand registerCommand2 = new RegisterCommand(validBorrower);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_BORROWER, () -> registerCommand2.execute(modelManager));
    }

    @Test
    public void equals() {
        Borrower a = new BorrowerBuilder().withName("A").actualBuild();
        Borrower b = new BorrowerBuilder().withName("B").actualBuild();
        RegisterCommand registerACommand = new RegisterCommand(a);
        RegisterCommand registerBCommand = new RegisterCommand(b);

        // same object -> returns true
        assertTrue(registerACommand.equals(registerACommand));

        // same values -> returns true
        RegisterCommand registerACommandCopy = new RegisterCommand(a);
        assertTrue(registerACommand.equals(registerACommandCopy));

        // different types -> returns false
        assertFalse(registerACommand.equals(1));

        // null -> returns false
        assertFalse(registerACommand.equals(null));

        // different person -> returns false
        assertFalse(registerACommand.equals(registerBCommand));
    }
}
