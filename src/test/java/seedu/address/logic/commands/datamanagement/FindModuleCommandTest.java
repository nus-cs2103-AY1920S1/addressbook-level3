package seedu.address.logic.commands.datamanagement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TypicalModuleHashMap;
import seedu.address.testutil.TypicalModulesInfo;
import seedu.address.ui.ResultViewType;

public class FindModuleCommandTest {

    @Test
    public void constructor_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindModuleCommand(null));
    }

    @Test
    public void execute_modulePresentInStudyPlan_findSuccessful() {
        // construct module
        Module cs1101s = TypicalModuleHashMap.getTypicalModuleHashMap().get("CS1101S");
        HashMap<String, Module> moduleHashMap = new HashMap<String, Module>();
        moduleHashMap.put("CS1101S", cs1101s);

        // construct model containing study plan with module in certain semesters
        StudyPlan studyPlan = new StudyPlanBuilder().withModules(moduleHashMap).build();
        studyPlan.addModuleToSemester(cs1101s.getModuleCode(), SemesterName.Y1S1);
        studyPlan.addModuleToSemester(cs1101s.getModuleCode(), SemesterName.Y3S2);
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct list of semesters that should be shown
        UniqueSemesterList expectedList = new UniqueSemesterList();
        Semester semesterOne = new Semester(SemesterName.Y1S1);
        semesterOne.addModule(cs1101s);
        Semester semesterTwo = new Semester(SemesterName.Y3S2);
        semesterTwo.addModule(cs1101s);
        expectedList.add(semesterOne);
        expectedList.add(semesterTwo);

        // construct command to find module
        FindModuleCommand findModuleCommand = new FindModuleCommand(cs1101s.getModuleCode().toString());

        CommandResult expectedCommandResult = new CommandResult<Semester>(String.format(
                FindModuleCommand.MESSAGE_SUCCESS, cs1101s.getModuleCode().toString()),
                        ResultViewType.SEMESTER, expectedList.asUnmodifiableObservableList());

        assertCommandSuccess(findModuleCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_moduleNotPresentInStudyPlan_throwsCommandException() {
        // construct model containing study plan with no modules
        StudyPlan studyPlan = new StudyPlanBuilder().build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        // construct command to find module
        FindModuleCommand findModuleCommand = new FindModuleCommand("CS3230");

        assertThrows(CommandException.class, () -> findModuleCommand.execute(model),
                String.format(FindModuleCommand.MESSAGE_MODULE_NOT_FOUND, "CS3230"));
    }

    @Test
    public void equals() {
        FindModuleCommand findModuleCommand = new FindModuleCommand("CS3230");
        FindModuleCommand findOtherModuleCommand = new FindModuleCommand("CS2103");

        // same object -> returns true
        assertTrue(findModuleCommand.equals(findModuleCommand));

        // same values -> returns true
        FindModuleCommand findModuleCommandCopy = new FindModuleCommand("CS3230");
        assertTrue(findModuleCommand.equals(findModuleCommandCopy));

        // different types -> returns false
        assertFalse(findModuleCommand.equals(1));

        // null -> returns false
        assertFalse(findModuleCommand.equals(null));

        // different module -> returns false
        assertFalse(findModuleCommand.equals(findOtherModuleCommand));
    }
}
