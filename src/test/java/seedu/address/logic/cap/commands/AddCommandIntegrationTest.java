package seedu.address.logic.cap.commands;

import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.ModelCapManager;
import seedu.address.model.common.Module;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());
    }

    //    @Test
    //    public void execute_newModule_success() {
    //        Module validModule = new ModuleBuilder().build();
    //
    //        Model expectedModel = new ModelCapManager(model.getCapLog(), new CapUserPrefs());
    //        expectedModel.addModule(validModule);
    //
    //        assertCommandSuccess(new AddCommand(validModule), model,
    //                String.format(AddCommand.MESSAGE_SUCCESS, validModule), model);
    //    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module moduleInList = model.getCapLog().getModuleList().get(0);
        assertCommandFailure(new AddCommand(moduleInList), model, AddCommand.MESSAGE_DUPLICATE_MODULE);
    }

}
