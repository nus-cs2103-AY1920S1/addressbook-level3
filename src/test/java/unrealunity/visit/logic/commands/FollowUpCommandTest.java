package unrealunity.visit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unrealunity.visit.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import unrealunity.visit.commons.core.index.Index;

public class FollowUpCommandTest {

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FollowUpCommand(null, 0));
    }

    @Test
    public void equals() {

        FollowUpCommand followupCommand = new FollowUpCommand(Index.fromZeroBased(1), 1);
        FollowUpCommand followupCommand2 = new FollowUpCommand(Index.fromZeroBased(2), 2);

        assertTrue(followupCommand.equals(followupCommand));
        assertTrue(followupCommand2.equals(followupCommand2));

        assertFalse(followupCommand.equals(1));
        assertFalse(followupCommand2.equals(null));

        assertFalse(followupCommand.equals(followupCommand2));
    }

}
