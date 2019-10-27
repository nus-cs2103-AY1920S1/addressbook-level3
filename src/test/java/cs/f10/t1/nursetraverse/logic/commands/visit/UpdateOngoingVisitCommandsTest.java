package cs.f10.t1.nursetraverse.logic.commands.visit;

import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model, Begin, Cancel, Finish command, Parser) and unit tests for
 * {@code UpdateOngoingVisitCommand}.
 */
public class UpdateOngoingVisitCommandsTest {

    //private Model model = new ModelManager(getTypicalPatientBook(), new UserPrefs());

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateOngoingVisitCommand(null));
    }
}
