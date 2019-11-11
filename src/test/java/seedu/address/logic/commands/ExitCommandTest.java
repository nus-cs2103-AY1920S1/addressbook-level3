package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;

public class ExitCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    void execute_exit_success() {
        CommandResult expectedCommandResult =
                new CommandResultBuilder(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT).setExit().build();

        CommandResult actualCommandResult =
                new ExitCommand().execute(model);

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new ExitCommand().equals(null));
    }

    @Test
    void equals_otherCommand() {
        assertFalse(new ExitCommand()
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals() {
        assertTrue(new ExitCommand().equals(new ExitCommand()));
    }
}
