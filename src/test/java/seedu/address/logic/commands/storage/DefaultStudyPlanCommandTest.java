package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModulesInfo;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.util.DefaultStudyPlanUtil;
import seedu.address.storage.JsonModulesInfoStorage;
import seedu.address.storage.ModulesInfoStorage;


/**
 * Contains integration tests (interaction with the Model) for {@code DefaultStudyPlanCommand}.
 */
public class DefaultStudyPlanCommandTest {

    private Model model;
    private ModulesInfo modulesInfo;

    @BeforeEach
    public void setUp() {
        ModulesInfoStorage modulesInfoStorage = new JsonModulesInfoStorage(Paths.get("modules_cs.json"));
        modulesInfo = initModulesInfo(modulesInfoStorage);
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), modulesInfo);
    }

    @Test
    public void execute_defaultStudyPlan_success() throws CommandException {
        StudyPlan defaultStudyPlan = DefaultStudyPlanUtil.getDefaultStudyPlan(modulesInfo);

        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), modulesInfo);
        expectedModel.addStudyPlan(defaultStudyPlan);
        expectedModel.activateStudyPlan(defaultStudyPlan.getIndex());

        DefaultStudyPlanCommand command = new DefaultStudyPlanCommand();
        CommandResult expectedResult =
                new CommandResult(String.format(DefaultStudyPlanCommand.MESSAGE_SUCCESS, defaultStudyPlan.getIndex()),
                        true, false);
        CommandResult actualResult = command.execute(model);
        // note: assertCommandSuccess is not used due to different unique IDs of the two default study plans
        // check whether default plans have been added
        String expectedTitle = expectedModel.getActiveStudyPlan().getTitle().toString();
        String actualTitle = model.getActiveStudyPlan().getTitle().toString();
        assertEquals(expectedTitle, actualTitle);
        // check whether the front part of the message is the same
        String expectedMessage = expectedResult.getFeedbackToUser().substring(0, 40);
        String actualMessage = actualResult.getFeedbackToUser().substring(0, 40);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void equals() {
        DefaultStudyPlanCommand command1 = new DefaultStudyPlanCommand();
        DefaultStudyPlanCommand command2 = new DefaultStudyPlanCommand();

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same command -> returns true
        assertTrue(command1.equals(command2));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));
    }

    /**
     * Initialises modules info from storage.
     */
    protected ModulesInfo initModulesInfo(ModulesInfoStorage storage) {
        ModulesInfo initializedModulesInfo;
        try {
            Optional<ModulesInfo> prefsOptional = storage.readModulesInfo();
            initializedModulesInfo = prefsOptional.orElse(new ModulesInfo());
        } catch (DataConversionException e) {
            initializedModulesInfo = new ModulesInfo();
        } catch (IOException e) {
            initializedModulesInfo = new ModulesInfo();
        }
        return initializedModulesInfo;
    }

}
