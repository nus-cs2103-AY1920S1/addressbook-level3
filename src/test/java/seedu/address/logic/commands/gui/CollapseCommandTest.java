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

class CollapseCommandTest {

    @Test
    public void constructor_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CollapseCommand(null));
    }

    @Test
    public void execute_semesterExpanded_collapseSuccessful()
            throws CommandException {
        CollapseCommand collapseCommand = new CollapseCommand(SemesterName.Y2S1);

        StudyPlan studyPlan = new StudyPlanBuilder().withSemesters(TypicalSemesterList.EMPTY_SEMESTER_LIST).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();
        model.getSemester(SemesterName.Y2S1).setExpanded(true);

        StudyPlan expectedStudyPlan = new StudyPlanBuilder()
                .withSemesters(TypicalSemesterList.EMPTY_SEMESTER_LIST).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();
        expectedModel.getSemester(SemesterName.Y2S1).setExpanded(false);

        // construct command to collapse semester
        CommandResult res = collapseCommand.execute(model);
        assertEquals(model.getSemester(SemesterName.Y2S1).isExpanded(),
                expectedModel.getSemester(SemesterName.Y2S1).isExpanded());
        assertEquals(res.getFeedbackToUser(), String.format(CollapseCommand.MESSAGE_SUCCESS, "Y2S1"));
    }

    @Test
    void testEquals() {
        CollapseCommand collapseCommand =
                new CollapseCommand(SemesterName.Y1S1);
        CollapseCommand otherCollapseCommand =
                new CollapseCommand(SemesterName.Y1S2);

        // same object -> returns true
        assertEquals(collapseCommand, collapseCommand);

        // same values -> returns true
        CollapseCommand collapseCommandCopy =
                new CollapseCommand(SemesterName.Y1S1);
        assertEquals(collapseCommand, collapseCommandCopy);

        // different types -> returns false
        assertNotEquals(1, collapseCommand);

        // null -> returns false
        assertNotEquals(null, collapseCommand);

        // different sem -> returns false
        assertNotEquals(collapseCommand, otherCollapseCommand);
    }
}
