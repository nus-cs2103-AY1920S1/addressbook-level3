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
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.testutil.StudyPlanBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code CreateStudyPlanCommand}.
 */
public class CreateStudyPlanCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_newStudyPlan_success() throws CommandException {
        StudyPlan validStudyPlan = new StudyPlanBuilder().build();
        Model expectedModel = generateExpectedModel(validStudyPlan);

        CreateStudyPlanCommand command = new CreateStudyPlanCommand(validStudyPlan.getTitle().toString());
        CommandResult expectedResult = new CommandResult(String.format(CreateStudyPlanCommand.MESSAGE_SUCCESS,
                validStudyPlan.getTitle().toString(), validStudyPlan.getIndex()), true, false);
        CommandResult actualResult = command.execute(model);

        assertCreateStudyPlanSuccess(expectedResult, actualResult, expectedModel);
    }

    @Test
    public void execute_newStudyPlanWithNoTitle_success() throws CommandException {
        StudyPlan validStudyPlanWithoutTitle = new StudyPlanBuilder().withTitle(new Title("")).build();
        Model expectedModel = generateExpectedModel(validStudyPlanWithoutTitle);

        CreateStudyPlanCommand command = new CreateStudyPlanCommand("");
        CommandResult expectedResult = new CommandResult(String.format(CreateStudyPlanCommand.MESSAGE_SUCCESS,
                validStudyPlanWithoutTitle.getTitle().toString(), validStudyPlanWithoutTitle.getIndex()),
                true, false);
        CommandResult actualResult = command.execute(model);

        assertCreateStudyPlanSuccess(expectedResult, actualResult, expectedModel);
    }

    /**
     * Generates the expected model given a study plan to be included, for testing.
     * @param studyPlan to be added to the model
     * @return the expected model for testing
     */
    private Model generateExpectedModel(StudyPlan studyPlan) {
        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.addStudyPlan(studyPlan);
        expectedModel.activateStudyPlan(studyPlan.getIndex());
        expectedModel.addToHistory();
        return expectedModel;
    }

    /**
     * Compares and returns true if the two command results have the same preceding feeback to user
     * (without study plan IDs), and whether they have the same title. This is because no two study plans
     * have the same unique ID.
     */
    private void assertCreateStudyPlanSuccess(CommandResult expectedResult, CommandResult actualResult,
                                              Model expectedModel) {
        // compare titles since study plan IDs are unique
        String expectedResultTitle = expectedResult.getFeedbackToUser().split("unique")[0];
        String actualResultTitle = actualResult.getFeedbackToUser().split("unique")[0];
        assertEquals(expectedResultTitle, actualResultTitle);
        assertEquals(expectedModel.getActiveStudyPlan().getTitle(), model.getActiveStudyPlan().getTitle());
    }

}
