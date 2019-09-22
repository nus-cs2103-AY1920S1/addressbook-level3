package seedu.algobase.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.ALICE;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.exceptions.DuplicateProblemException;
import seedu.algobase.testutil.ProblemBuilder;


public class AlgoBaseTest {

    private final AlgoBase algoBase = new AlgoBase();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), algoBase.getProblemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> algoBase.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAlgoBase_replacesData() {
        AlgoBase newData = getTypicalAlgoBase();
        algoBase.resetData(newData);
        assertEquals(newData, algoBase);
    }

    @Test
    public void resetData_withDuplicateProblems_throwsDuplicateProblemException() {
        // Two problems with the same identity fields
        Problem editedAlice = new ProblemBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        List<Problem> newProblems = Arrays.asList(ALICE, editedAlice);
        AlgoBaseStub newData = new AlgoBaseStub(newProblems);

        assertThrows(DuplicateProblemException.class, () -> algoBase.resetData(newData));
    }

    @Test
    public void hasProblem_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> algoBase.hasProblem(null));
    }

    @Test
    public void hasProblem_problemNotInAlgoBase_returnsFalse() {
        assertFalse(algoBase.hasProblem(ALICE));
    }

    @Test
    public void hasProblem_problemInAlgoBase_returnsTrue() {
        algoBase.addProblem(ALICE);
        assertTrue(algoBase.hasProblem(ALICE));
    }

    @Test
    public void hasProblem_problemWithSameIdentityFieldsInAlgoBase_returnsTrue() {
        algoBase.addProblem(ALICE);
        Problem editedAlice = new ProblemBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(algoBase.hasProblem(editedAlice));
    }

    @Test
    public void getProblemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> algoBase.getProblemList().remove(0));
    }

    /**
     * A stub ReadOnlyAlgoBase whose problems list can violate interface constraints.
     */
    private static class AlgoBaseStub implements ReadOnlyAlgoBase {
        private final ObservableList<Problem> problems = FXCollections.observableArrayList();

        AlgoBaseStub(Collection<Problem> problems) {
            this.problems.setAll(problems);
        }

        @Override
        public ObservableList<Problem> getProblemList() {
            return problems;
        }
    }

}
