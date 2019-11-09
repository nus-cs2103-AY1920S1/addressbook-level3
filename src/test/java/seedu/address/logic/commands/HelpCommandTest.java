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

public class HelpCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void execute_help_success() {
        CommandResult actualCommandResult =
                new HelpCommand().execute(model);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(HelpCommand.SHOWING_HELP_MESSAGE).setShowHelp().build();

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new HelpCommand().equals(null));
    }

    @Test
    void equals_otherCommand() {
        assertFalse(new HelpCommand()
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals() {
        assertTrue(new HelpCommand().equals(new HelpCommand()));
    }
}
