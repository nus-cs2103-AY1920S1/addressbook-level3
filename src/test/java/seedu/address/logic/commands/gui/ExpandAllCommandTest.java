package seedu.address.logic.commands.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TypicalModulesInfo;
import seedu.address.testutil.TypicalSemesterList;


class ExpandAllCommandTest {
    @Test
    public void execute_semesterExpanded_collapseSuccessful()
            throws CommandException {
        ExpandAllCommand expandAllCommand = new ExpandAllCommand();

        StudyPlan studyPlan = new StudyPlanBuilder().withSemesters(TypicalSemesterList.EMPTY_SEMESTER_LIST).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();
        model.getSemester(SemesterName.Y1S1).setExpanded(false);
        model.getSemester(SemesterName.Y1S2).setExpanded(false);
        model.getSemester(SemesterName.Y2S1).setExpanded(false);
        model.getSemester(SemesterName.Y2S2).setExpanded(false);
        model.getSemester(SemesterName.Y3S1).setExpanded(false);
        model.getSemester(SemesterName.Y3S2).setExpanded(false);
        model.getSemester(SemesterName.Y4S1).setExpanded(false);
        model.getSemester(SemesterName.Y4S2).setExpanded(false);

        StudyPlan expectedStudyPlan = new StudyPlanBuilder()
                .withSemesters(TypicalSemesterList.EMPTY_SEMESTER_LIST).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();
        for (Semester s : expectedModel.getSemestersFromActiveSp()) {
            s.setExpanded(true);
        }

        // construct command to collapse semester
        CommandResult res = expandAllCommand.execute(model);
        assertEquals(model.getSemester(SemesterName.Y1S1).isExpanded(),
                expectedModel.getSemester(SemesterName.Y1S1).isExpanded());
        assertEquals(model.getSemester(SemesterName.Y1S2).isExpanded(),
                expectedModel.getSemester(SemesterName.Y1S2).isExpanded());
        assertEquals(model.getSemester(SemesterName.Y2S1).isExpanded(),
                expectedModel.getSemester(SemesterName.Y2S1).isExpanded());
        assertEquals(model.getSemester(SemesterName.Y2S2).isExpanded(),
                expectedModel.getSemester(SemesterName.Y2S2).isExpanded());
        assertEquals(model.getSemester(SemesterName.Y3S1).isExpanded(),
                expectedModel.getSemester(SemesterName.Y3S1).isExpanded());
        assertEquals(model.getSemester(SemesterName.Y3S2).isExpanded(),
                expectedModel.getSemester(SemesterName.Y3S2).isExpanded());
        assertEquals(model.getSemester(SemesterName.Y4S1).isExpanded(),
                expectedModel.getSemester(SemesterName.Y4S1).isExpanded());
        assertEquals(model.getSemester(SemesterName.Y4S2).isExpanded(),
                expectedModel.getSemester(SemesterName.Y4S2).isExpanded());
        assertEquals(res.getFeedbackToUser(), ExpandAllCommand.MESSAGE_SUCCESS);
    }

    @Test
    void testEquals() {
        ExpandAllCommand expandAllCommand =
                new ExpandAllCommand();
        ExpandAllCommand otherExpandAllCommand =
                new ExpandAllCommand();

        // same object -> returns true
        assertEquals(expandAllCommand, expandAllCommand);

        // same values -> returns true
        assertEquals(expandAllCommand, otherExpandAllCommand);

        // different types -> returns false
        assertNotEquals(1, expandAllCommand);

        // null -> returns false
        assertNotEquals(null, expandAllCommand);
    }
}
