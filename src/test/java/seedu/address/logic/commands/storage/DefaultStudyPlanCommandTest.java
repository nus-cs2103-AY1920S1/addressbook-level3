package seedu.address.logic.commands.storage;

import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModulesInfo;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonModulesInfoStorage;
import seedu.address.storage.ModulesInfoStorage;


/**
 * Contains integration tests (interaction with the Model) for {@code DefaultStudyPlanCommand}.
 */
public class DefaultStudyPlanCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_commitStudyPlan_success() {
        ModulesInfoStorage modulesInfoStorage = new JsonModulesInfoStorage(Paths.get("modules_cs.json"));
        ModulesInfo modulesInfo = initModulesInfo(modulesInfoStorage);
        /*
        // TODO: add a full modulesinfo
        StudyPlan defaultStudyPlan = DefaultStudyPlanUtil.getDefaultStudyPlan(getTypicalModulesInfo());

        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.addStudyPlan(defaultStudyPlan);
        expectedModel.activateStudyPlan(defaultStudyPlan.getIndex());

        DefaultStudyPlanCommand command = new DefaultStudyPlanCommand();
        CommandResult expectedResult =
                new CommandResult(DefaultStudyPlanCommand.MESSAGE_SUCCESS, true, false);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
         */
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
