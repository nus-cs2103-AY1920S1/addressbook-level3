package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProblems.getTypicalAlgoBase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.Problem.Problem;
import seedu.address.testutil.ProblemBuilder;

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
