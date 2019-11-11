package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Tutorial;

public class DeleteTutorialVerifiedCommandTest {

    @Test
    public void execute_model_tutorialToBeDeleted() {
        ModuleBuilder.DEFAULT_TUTORIALS.clear();
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Module validModule = new ModuleBuilder().withModCode(VALID_MODULE_AMY).build();
        Tutorial validTutorial = new TutorialBuilder().withTutName(VALID_TUTORIAL_NAME_AMY)
                .withModCode(VALID_MODULE_AMY).build();

        Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalApplication(), new UserPrefs());

        expectedModel.addModule(validModule);
        model.addModule(validModule);

        model.addTutorial(validTutorial);
        model.addTutorialToModule(validTutorial);

        assertCommandSuccess(new DeleteTutorialVerifiedCommand(validTutorial), model,
                "Deleted Tutorial: " + validTutorial.toString(), expectedModel);

    }

    @Test
    public void needsInput_returnsTrue() {
        Tutorial validTutorial = new TutorialBuilder().withTutName(VALID_TUTORIAL_NAME_AMY)
                .withModCode(VALID_MODULE_AMY).build();
        DeleteTutorialVerifiedCommand deleteTutorialVerifiedCommand = new DeleteTutorialVerifiedCommand(validTutorial);

        assertTrue(deleteTutorialVerifiedCommand.needsInput());
    }

    @Test
    public void needsCommand_confirmsYesCommand_returnsTrue() {
        Tutorial validTutorial = new TutorialBuilder().withTutName(VALID_TUTORIAL_NAME_AMY)
                .withModCode(VALID_MODULE_AMY).build();
        DeleteTutorialVerifiedCommand deleteTutorialVerifiedCommand = new DeleteTutorialVerifiedCommand(validTutorial);

        ConfirmNoCommand confirmNoCommand = new ConfirmNoCommand();

        assertTrue(deleteTutorialVerifiedCommand.needsCommand(confirmNoCommand));

    }

    @Test
    public void needsCommand_confirmNoCommand_returnsTrue() {
        Tutorial validTutorial = new TutorialBuilder().withTutName(VALID_TUTORIAL_NAME_AMY)
                .withModCode(VALID_MODULE_AMY).build();
        DeleteTutorialVerifiedCommand deleteTutorialVerifiedCommand = new DeleteTutorialVerifiedCommand(validTutorial);
        ConfirmYesCommand confirmYesCommand = new ConfirmYesCommand();
        assertTrue(deleteTutorialVerifiedCommand.needsCommand(confirmYesCommand));

    }
}
