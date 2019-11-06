package seedu.address.logic.commands.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModuleHashMap.getTypicalModuleHashMap;
import static seedu.address.testutil.TypicalSemesterList.TYPICAL_SEMESTER_LIST;
import static seedu.address.testutil.TypicalSemesterList.TYPICAL_SEMESTER_LIST_WITH_CS1101S;

import java.util.Arrays;

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

class RedoCommandTest {
    @Test
    public void addMod_undo() throws CommandException {
        AddModuleCommand addModule = new AddModuleCommand(Arrays.asList("CS1101S"), SemesterName.Y1S1);
        StudyPlan studyPlan = new StudyPlanBuilder()
                .withModules(getTypicalModuleHashMap())
                .withSemesters(TYPICAL_SEMESTER_LIST).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        StudyPlan expectedStudyPlan = new StudyPlanBuilder()
                .withModules(getTypicalModuleHashMap())
                .withSemesters(TYPICAL_SEMESTER_LIST_WITH_CS1101S).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();

        // construct command to add a module
        addModule.execute(model);
        new UndoCommand().execute(model);
        CommandResult res = new RedoCommand().execute(model);
        assertEquals(model.getActiveStudyPlan(), expectedModel.getActiveStudyPlan());
        assertEquals(res.getFeedbackToUser(), RedoCommand.MESSAGE_SUCCESS);
    }
}
