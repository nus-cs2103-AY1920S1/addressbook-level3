package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.problem.AddCommand;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.testutil.ProblemBuilder;

class AddCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_problemAcceptedByModel_addsSuccessful() throws Exception {
        ModelStubAcceptingProblemAdded modelStub = new ModelStubAcceptingProblemAdded();
        Problem validProblem = new ProblemBuilder().build();

        CommandResult commandResult = new AddCommand(validProblem).execute(modelStub, commandHistory);

        assertEquals(
            String.format(AddCommand.MESSAGE_SUCCESS, validProblem.getName()),
            commandResult.getFeedbackToUser()
        );
        assertEquals(Collections.singletonList(validProblem), modelStub.problemsAdded);
    }

    /**
     * A Model stub that always accept the problem being added.
     */
    private static class ModelStubAcceptingProblemAdded extends DefaultModelStub {
        final ArrayList<Problem> problemsAdded = new ArrayList<>();
        final ArrayList<Tag> tagsAdded = new ArrayList<>();

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
        public void addTag(Tag tag) {
            requireNonNull(tag);
            tagsAdded.add(tag);
        }

        @Override
        public void addTags(Set<Tag> tags) {
            for (Tag tag : tags) {
                addTag(tag);
            }
        }

        @Override
        public ReadOnlyAlgoBase getAlgoBase() {
            return new AlgoBase();
        }
    }

}
