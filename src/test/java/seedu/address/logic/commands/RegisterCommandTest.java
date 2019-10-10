package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RegisterCommandTest {

    @Test
    public void constructor_nullBorrower_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RegisterCommand(null));
    }
}
