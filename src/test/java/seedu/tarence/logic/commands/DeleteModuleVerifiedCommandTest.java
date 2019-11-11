package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.module.Module;

public class DeleteModuleVerifiedCommandTest {


    public static final String VALID_MOD_CODE = "GER1000";

    @Test
    public void execute_model_moduleDeleted() {
        ModuleBuilder.DEFAULT_TUTORIALS.clear();
        Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalApplication(), new UserPrefs());
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        model.addModule(validModule);


        assertCommandSuccess(new DeleteModuleVerifiedCommand(validModule), model,
                "Deleted Module: " + VALID_MOD_CODE + " | ", expectedModel);
    }

    @Test
    public void needsInput_returnsTrue() {
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        DeleteModuleVerifiedCommand deleteModuleVerifiedCommand = new DeleteModuleVerifiedCommand(validModule);

        assertTrue(deleteModuleVerifiedCommand.needsInput());
    }

    @Test
    public void needsCommand_confirmsYesCommand_returnsTrue() {
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        DeleteModuleVerifiedCommand deleteModuleVerifiedCommand = new DeleteModuleVerifiedCommand(validModule);

        ConfirmNoCommand confirmNoCommand = new ConfirmNoCommand();

        assertTrue(deleteModuleVerifiedCommand.needsCommand(confirmNoCommand));

    }

    @Test
    public void needsCommand_confirmNoCommand_returnsTrue() {
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        DeleteModuleVerifiedCommand deleteModuleVerifiedCommand = new DeleteModuleVerifiedCommand(validModule);
        ConfirmYesCommand confirmYesCommand = new ConfirmYesCommand();
        assertTrue(deleteModuleVerifiedCommand.needsCommand(confirmYesCommand));

    }


}

