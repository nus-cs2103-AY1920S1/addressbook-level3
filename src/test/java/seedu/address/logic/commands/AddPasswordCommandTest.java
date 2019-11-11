package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.password.Password;
import seedu.address.testutil.PasswordBuilder;


public class AddPasswordCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPasswordCommand(null));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Password validPassword = new PasswordBuilder().build();
        AddPasswordCommand addPasswordCommand = new AddPasswordCommand(validPassword);
        ModelStub modelStub = new ModelStubWithPassword(validPassword);
        assertThrows(CommandException.class,
                AddPasswordCommand.MESSAGE_DUPLICATE_PASSWORD, () -> addPasswordCommand.execute(modelStub));
    }
}
