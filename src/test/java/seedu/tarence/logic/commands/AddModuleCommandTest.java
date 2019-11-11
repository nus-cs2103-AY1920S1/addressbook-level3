package seedu.tarence.logic.commands;

import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.module.exceptions.DuplicateModuleException;

public class AddModuleCommandTest {
    public static final String VALID_MOD_CODE = "GER1000";

    @Test
    public void execute_correctArguments_moduleAdded() {
        Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalApplication(), new UserPrefs());
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();

        expectedModel.addModule(validModule);

        assertCommandSuccess(new AddModuleCommand(validModule), model,
                String.format(AddModuleCommand.MESSAGE_SUCCESS, validModule), expectedModel);
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null));
    }

    @Test
    public void execute_duplicateModule_throwsDuplicateException() {
        Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
        Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        model.addModule(validModule);

        assertThrows(DuplicateModuleException.class, () -> model.addModule(validModule));
    }


}
