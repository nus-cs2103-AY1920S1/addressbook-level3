package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;

class ShowNusModCommandTest {

    private static ModelManager model;

    @BeforeAll
    static void setUp() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateModelWithNusModsData();
    }

    /**
     * Will trigger browser to open up, disabled for now.
     */
    @Disabled
    void execute_success_commandResultSuccess() throws CommandException {
        ModuleCode moduleCode = new ModuleCode("CS3230");

        CommandResult actualCommandResult = new ShowNusModCommand(moduleCode).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(ShowNusModCommand.MESSAGE_SUCCESS, moduleCode));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_invalidModuleCode_commandResultFailure() throws CommandException {
        String invalidModuleCode = "INVALID_MODULE_CODE";
        ModuleCode moduleCode = new ModuleCode(invalidModuleCode);

        CommandResult actualCommandResult = new ShowNusModCommand(moduleCode).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                        String.format(ShowNusModCommand.MESSAGE_MODULE_NOT_FOUND, invalidModuleCode));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void equals() {
        ModuleCode moduleCode = new ModuleCode("CS3230");
        ModuleCode moduleCode2 = new ModuleCode("CS2103T");

        ShowNusModCommand command = new ShowNusModCommand(moduleCode);
        assertTrue(command.equals(command));
        assertTrue(command.equals(new ShowNusModCommand(moduleCode)));
        assertFalse(command.equals(new ShowNusModCommand(moduleCode2)));
    }
}
