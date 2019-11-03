package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.person.Name;

public class EmailCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_emailInvalidName_invalidPerson() {
        assertCommandFailure(new EmailCommand("timeslot", new Name("Alice")), model,
                Messages.MESSAGE_INVALID_PERSON_NAME);
    }
}
