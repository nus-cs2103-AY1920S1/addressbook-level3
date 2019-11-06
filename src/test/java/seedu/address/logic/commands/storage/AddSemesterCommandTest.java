package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModulePlanner;
import seedu.address.model.UserPrefs;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.testutil.ModulePlannerBuilder;

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

    @Test
    public void execute_addDuplicatedSemester_throwsCommandException() throws CommandException {
        AddSemesterCommand command = new AddSemesterCommand(SemesterName.Y1S1);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_nullActiveStudyPlan_throwsCommandException() throws CommandException {
        ModulePlanner nullActiveStudyPlanModulePlanner = new ModulePlannerBuilder().build();
        Model nullActiveStudyPlanModel =
                new ModelManager(nullActiveStudyPlanModulePlanner, new UserPrefs(), getTypicalModulesInfo());
        AddSemesterCommand command = new AddSemesterCommand(SemesterName.Y1ST1);
        assertThrows(CommandException.class, () -> command.execute(nullActiveStudyPlanModel));
    }

    @Test
    public void equals() {
        AddSemesterCommand command1 = new AddSemesterCommand(SemesterName.Y1S1);
        AddSemesterCommand command2 = new AddSemesterCommand(SemesterName.Y2S2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        AddSemesterCommand command3 = new AddSemesterCommand(SemesterName.Y1S1);
        assertTrue(command1.equals(command3));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different semesters -> returns false
        assertFalse(command1.equals(command2));
    }

}
