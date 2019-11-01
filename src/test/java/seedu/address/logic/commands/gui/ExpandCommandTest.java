package seedu.address.logic.commands.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import seedu.address.testutil.TypicalSemesterList;

class ExpandCommandTest {

    @Test
    public void constructor_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpandCommand(null));
    }

    @Test
    public void execute_semesterExpanded_collapseSuccessful()
            throws CommandException {
        ExpandCommand expandCommand = new ExpandCommand(SemesterName.Y2S1);

        StudyPlan studyPlan = new StudyPlanBuilder().withSemesters(TypicalSemesterList.EMPTY_SEMESTER_LIST).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();
        model.getSemester(SemesterName.Y2S1).setExpanded(false);

        StudyPlan expectedStudyPlan = new StudyPlanBuilder()
                .withSemesters(TypicalSemesterList.EMPTY_SEMESTER_LIST).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();
        expectedModel.getSemester(SemesterName.Y2S1).setExpanded(true);

        // construct command to collapse semester
        CommandResult res = expandCommand.execute(model);
        assertEquals(model.getSemester(SemesterName.Y2S1).isExpanded(),
                expectedModel.getSemester(SemesterName.Y2S1).isExpanded());
        assertEquals(res.getFeedbackToUser(), String.format(ExpandCommand.MESSAGE_SUCCESS, "Y2S1"));
    }

    @Test
    void testEquals() {
        ExpandCommand expandCommand =
                new ExpandCommand(SemesterName.Y1S1);
        ExpandCommand otherExpandCommand =
                new ExpandCommand(SemesterName.Y1S2);

        // same object -> returns true
        assertEquals(expandCommand, expandCommand);

        // same values -> returns true
        ExpandCommand expandCommandCopy =
                new ExpandCommand(SemesterName.Y1S1);
        assertEquals(expandCommand, expandCommandCopy);

        // different types -> returns false
        assertNotEquals(1, expandCommand);

        // null -> returns false
        assertNotEquals(null, expandCommand);

        // different sem -> returns false
        assertNotEquals(expandCommand, otherExpandCommand);
    }
}
