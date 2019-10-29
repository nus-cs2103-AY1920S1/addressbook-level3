package seedu.address.logic.commands.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalModule.CS1101S;
import static seedu.address.testutil.TypicalModule.CS2102;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalModule.CS5219;
import static seedu.address.testutil.TypicalModule.CS5339;
import static seedu.address.testutil.TypicalModule.ST2334;
import static seedu.address.testutil.TypicalSemesterList.TYPICAL_SEMESTER_LIST;
import static seedu.address.testutil.TypicalSemesterList.TYPICAL_SEMESTER_LIST_WITH_CS1101S;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalStudyPlan;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TypicalModulesInfo;

class AddModuleCommandTest {
    @Test
    public void constructor_nullTagName_throwsNullPointerException() {
        String validModuleCode = CS1101S.getModuleCode().toString();
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(validModuleCode, null));
    }

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null, SemesterName.Y1S1));
    }

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null, null));
    }


    @Test
    public void execute_moduleNotPresentInStudyPlan_newModuleAddSuccessful()
            throws CloneNotSupportedException, CommandException {
        AddModuleCommand addModuleCommand = new AddModuleCommand("CS1101S", SemesterName.Y1S1);

        HashMap<String, Module> moduleHashMap = new HashMap<>();
        moduleHashMap.put("ST2334", ST2334);
        moduleHashMap.put("CS3244", CS3244);
        moduleHashMap.put("CS1101S", CS1101S);
        moduleHashMap.put("CS2102", CS2102);
        moduleHashMap.put("CS5339", CS5339);
        moduleHashMap.put("CS5219", CS5219);

        UniqueSemesterList uniqueSemesterList = TYPICAL_SEMESTER_LIST.clone();

        // construct model containing study plan without CS1101S in Y1S1
        StudyPlan studyPlan = new StudyPlanBuilder()
                .withModules(moduleHashMap).withSemesters(uniqueSemesterList).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        UniqueSemesterList expectedSemesterList = TYPICAL_SEMESTER_LIST_WITH_CS1101S.clone();

        // construct expected model containing study plan with CS1101S in Y1S1
        StudyPlan expectedStudyPlan = new StudyPlanBuilder()
                .withModules(moduleHashMap).withSemesters(expectedSemesterList).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();
        expectedModel.addToHistory();

        // construct command to add a module
        CommandResult res = addModuleCommand.execute(model);
        assertEquals(model.getActiveStudyPlan(), expectedModel.getActiveStudyPlan());
        assertEquals(res.getFeedbackToUser(), String.format(AddModuleCommand.MESSAGE_SUCCESS,
                CS1101S.getModuleCode().toString(), "Y1S1"));
    }

    @Test
    public void execute_invalidModuleCode_throwsException() {
        StudyPlan studyPlan = getTypicalStudyPlan();

        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        AddModuleCommand addModuleCommand = new AddModuleCommand("CS3133", SemesterName.Y1S1);
        assertThrows(CommandException.class, () -> addModuleCommand.execute(model),
                AddModuleCommand.MESSAGE_MODULE_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        AddModuleCommand addModuleCommand = new AddModuleCommand("CS3244", SemesterName.Y1S2);
        AddModuleCommand otherAddModuleCommand = new AddModuleCommand("CS3233", SemesterName.Y1S1);

        // same object -> returns true
        assertTrue(addModuleCommand.equals(addModuleCommand));

        // same values -> returns true
        AddModuleCommand addModuleCommandCopy = new AddModuleCommand("CS3244", SemesterName.Y1S2);
        assertTrue(addModuleCommand.equals(addModuleCommandCopy));

        // different types -> returns false
        assertFalse(addModuleCommand.equals(1));

        // null -> returns false
        assertFalse(addModuleCommand.equals(null));

        // different module code -> returns false
        assertFalse(addModuleCommand.equals(otherAddModuleCommand));
    }
}
