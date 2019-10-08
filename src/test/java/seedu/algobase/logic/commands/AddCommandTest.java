package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.testutil.ProblemBuilder;

class AddCommandTest {
    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_problemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProblemAdded modelStub = new ModelStubAcceptingProblemAdded();
        Problem validProblem = new ProblemBuilder().build();

        CommandResult commandResult = new AddCommand(validProblem).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validProblem), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProblem), modelStub.problemsAdded);
    }

    /**
     * A Model stub that always accept the problem being added.
     */
    private class ModelStubAcceptingProblemAdded extends DefaultModelStub {
        final ArrayList<Problem> problemsAdded = new ArrayList<>();

        @Override
        public boolean hasProblem(Problem problem) {
            requireNonNull(problem);
            return problemsAdded.stream().anyMatch(problem::isSameProblem);
        }

        @Override
        public void addProblem(Problem problem) {
            requireNonNull(problem);
            problemsAdded.add(problem);
        }

        @Override
        public ReadOnlyAlgoBase getAlgoBase() {
            return new AlgoBase();
        }
    }
}
