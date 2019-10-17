package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Name;

public class EmailCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_emailInvalidName_invalidPerson() {
        assertCommandFailure(new EmailCommand(new Name("Alice")), model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }
}
