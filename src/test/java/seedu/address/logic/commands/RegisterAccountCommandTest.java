package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RegisterAccountCommandTest {

    @Test
    public void constructor_nullAccount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RegisterAccountCommand(null));
    }

}
