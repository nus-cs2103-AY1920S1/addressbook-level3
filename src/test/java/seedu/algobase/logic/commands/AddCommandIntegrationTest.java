package seedu.algobase.logic.commands;

import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.testutil.ProblemBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    }

    @Test
    public void execute_newProblem_success() {
        Problem validProblem = new ProblemBuilder().build();

        Model expectedModel = new ModelManager(model.getAlgoBase(), new UserPrefs());
        expectedModel.addProblem(validProblem);

        assertCommandSuccess(new AddCommand(validProblem), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validProblem), expectedModel);
    }

    @Test
    public void execute_duplicateProblem_throwsCommandException() {
        Problem problemInList = model.getAlgoBase().getProblemList().get(0);
        assertCommandFailure(new AddCommand(problemInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
