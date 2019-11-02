package seedu.address.logic.commands.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalModule.CS1101S;
import static seedu.address.testutil.TypicalModule.CS2102;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalModule.CS5219;
import static seedu.address.testutil.TypicalModule.CS5339;
import static seedu.address.testutil.TypicalModule.ST2334;
import static seedu.address.testutil.TypicalSemesterList.TYPICAL_SEMESTER_LIST;
import static seedu.address.testutil.TypicalSemesterList.TYPICAL_SEMESTER_LIST_WITH_CS1101S;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalStudyPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public void constructor_nullSemester_throwsNullPointerException() {
        List<String> validModuleCodes = new ArrayList<>();
        validModuleCodes.add(CS1101S.getModuleCode().toString());
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(validModuleCodes, null));
    }

    @Test
    public void constructor_nullModuleCodes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null, SemesterName.Y1S1));
    }

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null, null));
    }

    @Test
    public void execute_moduleNotPresentInStudyPlan_newModuleAddSuccessful()
            throws CloneNotSupportedException, CommandException {
        List<String> validModuleCodes = new ArrayList<>();
        validModuleCodes.add(CS1101S.getModuleCode().toString());
        AddModuleCommand addModuleCommand = new AddModuleCommand(validModuleCodes, SemesterName.Y1S1);

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
    public void execute_invalidModuleCode_failure() throws CommandException {
        StudyPlan studyPlan = getTypicalStudyPlan();

        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();

        List<String> invalidModuleCodes = new ArrayList<>();
        invalidModuleCodes.add("CS3133");

        AddModuleCommand addModuleCommand = new AddModuleCommand(invalidModuleCodes, SemesterName.Y1S1);
        CommandResult res = addModuleCommand.execute(model);

        assertEquals(model.getActiveStudyPlan(), expectedModel.getActiveStudyPlan());
        assertEquals(res.getFeedbackToUser(), String.format(AddModuleCommand.MESSAGE_MODULE_DOES_NOT_EXIST,
                "CS3133"));
    }

    @Test
    public void execute_duplicateModule_failure() throws CommandException {
        StudyPlan studyPlan = getTypicalStudyPlan();

        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();

        List<String> validModuleCodes = new ArrayList<>();
        validModuleCodes.add(ST2334.getModuleCode().toString());

        AddModuleCommand addModuleCommand = new AddModuleCommand(validModuleCodes, SemesterName.Y1S1);
        CommandResult res = addModuleCommand.execute(model);

        assertEquals(model.getActiveStudyPlan(), expectedModel.getActiveStudyPlan());
        assertEquals(res.getFeedbackToUser(), String.format(AddModuleCommand.MESSAGE_DUPLICATE_MODULE,
                ST2334.getModuleCode().toString(), SemesterName.Y1S1));
    }

    @Test
    public void execute_semesterDoesNotExist_throwsException() {
        StudyPlan studyPlan = getTypicalStudyPlan();

        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        List<String> validModuleCodes = new ArrayList<>();
        validModuleCodes.add(CS1101S.getModuleCode().toString());

        AddModuleCommand addModuleCommand = new AddModuleCommand(validModuleCodes, SemesterName.Y1ST1);
        assertThrows(CommandException.class, () -> addModuleCommand.execute(model),
                String.format(AddModuleCommand.MESSAGE_SEMESTER_DOES_NOT_EXIST, "Y1ST1"));
    }

    @Test
    public void equals() {
        List<String> validModuleCodes = new ArrayList<>();
        validModuleCodes.add(CS3244.getModuleCode().toString());
        AddModuleCommand addModuleCommand = new AddModuleCommand(validModuleCodes, SemesterName.Y1S2);
        List<String> otherValidModuleCodes = new ArrayList<>();
        otherValidModuleCodes.add("CS3233");
        AddModuleCommand otherAddModuleCommand = new AddModuleCommand(otherValidModuleCodes, SemesterName.Y1S1);

        // same object -> returns true
        assertEquals(addModuleCommand, addModuleCommand);

        // same values -> returns true
        List<String> validModuleCodesCopy = new ArrayList<>();
        validModuleCodesCopy.add(CS3244.getModuleCode().toString());
        AddModuleCommand addModuleCommandCopy = new AddModuleCommand(validModuleCodes, SemesterName.Y1S2);
        assertEquals(addModuleCommand, addModuleCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addModuleCommand);

        // null -> returns false
        assertNotEquals(null, addModuleCommand);

        // different module codes -> returns false
        assertNotEquals(addModuleCommand, otherAddModuleCommand);
    }
}
