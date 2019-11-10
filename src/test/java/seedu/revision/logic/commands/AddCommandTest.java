package seedu.revision.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.stubs.ModelStub;
import seedu.revision.stubs.ModelStubAcceptingAnswerableAdded;
import seedu.revision.stubs.ModelStubWithAnswerable;
import seedu.revision.testutil.McqBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_answerableAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAnswerableAdded modelStub = new ModelStubAcceptingAnswerableAdded();
        Answerable validAnswerable = new McqBuilder().build();

        CommandResult commandResult = new AddCommand(validAnswerable).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validAnswerable), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAnswerable), modelStub.getAnswerablesAdded());
    }

    @Test
    public void execute_duplicateAnswerable_throwsCommandException() {
        Answerable validAnswerable = new McqBuilder().build();
        AddCommand addCommand = new AddCommand(validAnswerable);
        ModelStub modelStub = new ModelStubWithAnswerable(validAnswerable);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ANSWERABLE, () -> addCommand
                .execute(modelStub));
    }

    @Test
    public void equals() {
        Answerable alice = new McqBuilder().withQuestion("Alice").build();
        Answerable bob = new McqBuilder().withQuestion("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different answerable -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }


}
