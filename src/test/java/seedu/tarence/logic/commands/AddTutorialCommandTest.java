package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.testutil.ModuleBuilder;
import seedu.tarence.testutil.TutorialBuilder;

public class AddTutorialCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String VALID_TUT_NAME = "T02";

    @Test
    public void execute_tutorialAcceptedByModule_addSuccessful() throws Exception {
        Module module = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        AddTutorialCommandTest.ModelStubTutorialCommand modelStub = new AddTutorialCommandTest
                .ModelStubTutorialCommand();
        modelStub.modules.add(module);
        Tutorial validTutorial = new TutorialBuilder().withModCode(VALID_MOD_CODE).withTutName(VALID_TUT_NAME).build();

        CommandResult commandResult = new AddTutorialCommand(validTutorial).execute(modelStub);
        String expectedMessage = String.format(AddTutorialCommand.MESSAGE_SUCCESS, validTutorial,
                validTutorial.getTimeTable().getDay(), validTutorial.getTimeTable().getWeeks(),
                validTutorial.getTimeTable().getStartTime(), validTutorial.getTimeTable().getDuration().toMinutes());

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTutorial), modelStub.tutorials);
    }

    @Test
    public void execute_noSuchModule_throwsCommandException() {
        Tutorial tutorial = new TutorialBuilder().withModCode(VALID_MOD_CODE).withTutName(VALID_TUT_NAME).build();
        AddTutorialCommandTest.ModelStubTutorialCommand modelStub = new AddTutorialCommandTest
                .ModelStubTutorialCommand();
        AddTutorialCommand addTutorialCommand = new AddTutorialCommand(tutorial);

        assertThrows(CommandException.class,
                AddTutorialCommand.MESSAGE_INVALID_MODULE, () -> addTutorialCommand.execute(modelStub));
    }

    private class ModelStubTutorialCommand extends ModelStub {
        final ArrayList<Module> modules = new ArrayList<>();
        final ArrayList<Tutorial> tutorials = new ArrayList<>();

        @Override
        public void addTutorial(Tutorial tutorial) {
            tutorials.add(tutorial);
        }

        @Override
        public boolean hasModuleOfCode(ModCode modCode) {
            for (Module module : modules) {
                if (module.getModCode().equals(modCode)) {
                    return true;
                }
            }
            return false;
        }

    }
}
