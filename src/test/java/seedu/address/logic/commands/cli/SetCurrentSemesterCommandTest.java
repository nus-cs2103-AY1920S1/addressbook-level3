package seedu.address.logic.commands.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TypicalModulesInfo;

class SetCurrentSemesterCommandTest {
    @Test
    public void constructor_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetCurrentSemesterCommand(null));
    }


    @Test
    public void execute_moduleNotPresentInStudyPlan_newModuleAddSuccessful()
            throws CloneNotSupportedException, CommandException {
        SetCurrentSemesterCommand setCurrentSemesterCommand = new SetCurrentSemesterCommand(SemesterName.Y2S1);

        StudyPlan studyPlan = new StudyPlanBuilder().withCurrentSemester("Y1S2").build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withCurrentSemester("Y2S1").build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();
        expectedModel.addToHistory();

        // construct command to add a module
        CommandResult res = setCurrentSemesterCommand.execute(model);
        assertEquals(model.getActiveStudyPlan(), expectedModel.getActiveStudyPlan());
        assertEquals(res.getFeedbackToUser(), String.format(SetCurrentSemesterCommand.MESSAGE_SUCCESS, "Y2S1"));
    }

    @Test
    public void equals() {
        SetCurrentSemesterCommand setCurrentSemesterCommand =
                new SetCurrentSemesterCommand(SemesterName.Y1S1);
        SetCurrentSemesterCommand otherSetCurrentSemesterCommand =
                new SetCurrentSemesterCommand(SemesterName.Y1S2);

        // same object -> returns true
        assertTrue(setCurrentSemesterCommand.equals(setCurrentSemesterCommand));

        // same values -> returns true
        SetCurrentSemesterCommand setCurrentSemesterCommandCopy =
                new SetCurrentSemesterCommand(SemesterName.Y1S1);
        assertTrue(setCurrentSemesterCommand.equals(setCurrentSemesterCommandCopy));

        // different types -> returns false
        assertFalse(setCurrentSemesterCommand.equals(1));

        // null -> returns false
        assertFalse(setCurrentSemesterCommand.equals(null));

        // different module code -> returns false
        assertFalse(setCurrentSemesterCommand.equals(otherSetCurrentSemesterCommand));
    }
}
