package seedu.address.logic.commands.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModule.CS1101S;
import static seedu.address.testutil.TypicalModule.CS2102;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalModule.CS5219;
import static seedu.address.testutil.TypicalModule.CS5339;
import static seedu.address.testutil.TypicalModule.ST2334;
import static seedu.address.testutil.TypicalSemesterList.TYPICAL_SEMESTER_LIST;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.ModulePlannerBuilder;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.testutil.TypicalModulesInfo;

class UndoCommandTest {
    @Test
    public void addMod_undo() throws CommandException {
        AddModuleCommand addModule = new AddModuleCommand(Arrays.asList("CS1101S"), SemesterName.Y1S1);

        HashMap<String, Module> moduleHashMap = new HashMap<>();
        moduleHashMap.put("ST2334", ST2334);
        moduleHashMap.put("CS3244", CS3244);
        moduleHashMap.put("CS1101S", CS1101S);
        moduleHashMap.put("CS2102", CS2102);
        moduleHashMap.put("CS5339", CS5339);
        moduleHashMap.put("CS5219", CS5219);

        StudyPlan studyPlan = new StudyPlanBuilder()
                .withModules(moduleHashMap)
                .withSemesters(TYPICAL_SEMESTER_LIST).build();
        Model model = new ModelManager(new ModulePlannerBuilder().withStudyPlan(studyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        model.activateFirstStudyPlan();

        StudyPlan expectedStudyPlan = new StudyPlanBuilder()
                .withModules(moduleHashMap)
                .withSemesters(TYPICAL_SEMESTER_LIST).build();
        Model expectedModel = new ModelManager(new ModulePlannerBuilder().withStudyPlan(expectedStudyPlan).build(),
                new UserPrefs(), TypicalModulesInfo.getTypicalModulesInfo());
        expectedModel.activateFirstStudyPlan();

        // construct command to add a module
        addModule.execute(model);
        CommandResult res = new UndoCommand().execute(model);
        assertEquals(model.getActiveStudyPlan(), expectedModel.getActiveStudyPlan());
        assertEquals(res.getFeedbackToUser(), UndoCommand.MESSAGE_SUCCESS);
    }
}
