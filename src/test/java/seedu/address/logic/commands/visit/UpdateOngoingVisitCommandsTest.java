package seedu.address.logic.commands.visit;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model, Begin, Cancel, Finish command, Parser) and unit tests for
 * {@code UpdateOngoingVisitCommand}.
 */
public class UpdateOngoingVisitCommandsTest {

    //private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateOngoingVisitCommand(null));
    }
}
