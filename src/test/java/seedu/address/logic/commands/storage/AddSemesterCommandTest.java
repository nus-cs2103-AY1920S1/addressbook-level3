package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;

/**
 * Contains integration tests (interaction with the Model) for {@code AddSemesterCommand}.
 */
public class AddSemesterCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_addNonMainstreamSemester_success() throws CommandException {
        Semester semesterToAdd = new Semester(SemesterName.Y1ST1);
        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        UniqueSemesterList expectedSemesterList = expectedModel.getActiveStudyPlan().getSemesters();
        expectedSemesterList.add(semesterToAdd);
        expectedSemesterList.sortBySemesterName();

        AddSemesterCommand command = new AddSemesterCommand(SemesterName.Y1ST1);
        CommandResult expectedResult =
                new CommandResult(String.format(AddSemesterCommand.MESSAGE_SUCCESS, SemesterName.Y1ST1));
        CommandResult actualResult = command.execute(model);
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedModel.getActiveStudyPlan().getSemesters(), model.getActiveStudyPlan().getSemesters());
    }


    // TODO: implement later
    /*
    @Test
    public void execute_addMainstreamSemester_failure() throws CommandException {
        Semester semesterToAdd = new Semester(SemesterName.Y1ST1);
        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        UniqueSemesterList expectedSemesterList = expectedModel.getActiveStudyPlan().getSemesters();
        expectedSemesterList.add(semesterToAdd);
        expectedSemesterList.sortBySemesterName();

        AddSemesterCommand command = new AddSemesterCommand(SemesterName.Y1ST1);
        CommandResult expectedResult =
                new CommandResult(String.format(AddSemesterCommand.MESSAGE_SUCCESS, SemesterName.Y1ST1));
        CommandResult actualResult = command.execute(model);
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedModel.getActiveStudyPlan().getSemesters(), model.getActiveStudyPlan().getSemesters());
    }
     */

}
